package com.redvoluntarios.vistas.voluntario;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.activity.OnBackPressedCallback;

import com.google.android.material.imageview.ShapeableImageView;
import com.redvoluntarios.R;
import com.redvoluntarios.vistas.principal.MainActivity;
import com.redvoluntarios.vistas.principal.SesionManager;
import androidx.activity.OnBackPressedCallback;

/**
 * PANTALLA: PerfilVoluntarioActivity
 * ============================================================================
 * Permite visualizar y editar la información personal del voluntario,
 * tomar una nueva foto de perfil con la CÁMARA y revisar estadísticas.
 */
public class PerfilVoluntarioActivity extends AppCompatActivity {

    private EditText edtNombre, edtEmail, edtTelefono, edtComuna;
    private Button btnCambiarFoto, btnGuardar, btnCerrarSesion;
    private ShapeableImageView imgPerfilAvatar;

    private ActivityResultLauncher<Intent> camaraLauncher;
    private static final int REQUEST_CAMERA_PERMISSION = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_perfil_voluntario);

        edtNombre = findViewById(R.id.edtPerfilNombre);
        edtEmail = findViewById(R.id.edtPerfilEmail);
        edtTelefono = findViewById(R.id.edtPerfilTelefono);
        edtComuna = findViewById(R.id.edtPerfilComuna);
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto);
        btnGuardar = findViewById(R.id.btnPerfilGuardar);
        btnCerrarSesion = findViewById(R.id.btnPerfilCerrarSesion);
        imgPerfilAvatar = findViewById(R.id.imgPerfilAvatar);

        // Configuración de la captura de cámara
        camaraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle extras = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            imgPerfilAvatar.setImageBitmap(imageBitmap);
                        }
                    }
                }
        );

        // Acción de cambio de foto de perfil
        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PerfilVoluntarioActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PerfilVoluntarioActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    abrirCamaraSegura();
                }
            }
        });

        // Guardar cambios del formulario de perfil
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                if (nombre.isEmpty()) {
                    edtNombre.setError("El nombre no puede estar vacío");
                    return;
                }
                if (email.isEmpty()) {
                    edtEmail.setError("El correo no puede estar vacío");
                    return;
                }

                Toast.makeText(PerfilVoluntarioActivity.this,
                        "¡Perfil actualizado con éxito!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Cerrar sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar los datos persistentes de SharedPreferences
                new SesionManager(PerfilVoluntarioActivity.this).cerrarSesion();
                
                Toast.makeText(PerfilVoluntarioActivity.this,
                        "Has cerrado sesión correctamente",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PerfilVoluntarioActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // ------------------------------------------------------------------------
        // GESTIÓN DEL BOTÓN ATRÁS NATIVO DEL SISTEMA
        // ------------------------------------------------------------------------
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Terminar esta pantalla para regresar al Hub de Voluntarios
                finish();
            }
        });
    }

    /**
     * Método seguro para abrir la cámara evitando ActivityNotFoundException en Android 11+
     */
    private void abrirCamaraSegura() {
        Intent tomaFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            camaraLauncher.launch(tomaFotoIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No se encontró ninguna aplicación de cámara", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamaraSegura();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado. No se puede actualizar la foto.", Toast.LENGTH_LONG).show();
            }
        }
    }
}