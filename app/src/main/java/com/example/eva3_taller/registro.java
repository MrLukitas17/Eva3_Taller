package com.example.eva3_taller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registro extends AppCompatActivity {

    EditText Nombre, Rut, NombreUsuario, Password;
    Button btnRegistrar, btnCuentaExistente;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Nombre = findViewById(R.id.nombreregis);
        Rut = findViewById(R.id.correoresgis);
        NombreUsuario = findViewById(R.id.usuarioregis);
        Password = findViewById(R.id.passregis);
        btnRegistrar = findViewById(R.id.btninicioregis);
        btnCuentaExistente = findViewById(R.id.btncuentaexistente);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = Nombre.getText().toString().trim();
                String rut = Rut.getText().toString().trim();
                String nombreUsuario = NombreUsuario.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (!nombre.isEmpty() && !rut.isEmpty() && !nombreUsuario.isEmpty() && !password.isEmpty()) {
                    // Registro en Firebase Authentication
                    firebaseAuth.createUserWithEmailAndPassword(nombreUsuario, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_LONG).show();

                                        // Limpiar campos
                                        Nombre.setText("");
                                        Rut.setText("");
                                        NombreUsuario.setText("");
                                        Password.setText("");

                                        // Redirigir a la actividad de inicio de sesión
                                        Intent intent = new Intent(registro.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error al registrar usuario", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCuentaExistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirige al usuario a la actividad de inicio de sesión si ya tiene una cuenta
                Intent intent = new Intent(registro.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
