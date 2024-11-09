package com.example.eva3_taller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText txtNombre, txtPassword;
    Button btnAceptar, btnRegistro;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.usuariologin);
        txtPassword = findViewById(R.id.passlogin);
        btnAceptar = findViewById(R.id.btniniciologin);
        btnRegistro = findViewById(R.id.btnregistrologin);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuario = txtNombre.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (nombreUsuario.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar su nombre de usuario y contraseña", Toast.LENGTH_LONG).show();
                    return;
                }

                // Autenticación con Firebase
                firebaseAuth.signInWithEmailAndPassword(nombreUsuario, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                    Intent intentUsuario = new Intent(MainActivity.this, tienda.class);
                                    intentUsuario.putExtra("Nombre", nombreUsuario);
                                    startActivity(intentUsuario);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistro = new Intent(MainActivity.this, registro.class);
                startActivity(intentRegistro);
            }
        });
    }
}
