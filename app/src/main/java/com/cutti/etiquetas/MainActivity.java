package com.cutti.etiquetas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cutti.etiquetas.controller.Zebra;
import com.cutti.etiquetas.data.BuscaRazao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.ZebraPrintException;

public class MainActivity extends AppCompatActivity {

    private NumberPicker numeroEtiqueta;

    private TextInputEditText manifesto, codCliente;
    private RadioGroup tipo;
    private Button botaoImprime, boxButton;
    private RadioButton tipoSelect;
    private CheckBox doca1, doca2, doca3, doca4;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        //Indexa tudo
        manifesto = findViewById(R.id.manifesto);
        codCliente = findViewById(R.id.codCliente);
        tipo = findViewById(R.id.tipo);
        boxButton = findViewById(R.id.boxButton);
        numeroEtiqueta = findViewById(R.id.numeroEtiqueta);
        botaoImprime = findViewById(R.id.botaoImprime);

        textView = findViewById(R.id.textView);

        //checkbox
        doca1 = findViewById(R.id.doca1);
        doca2 = findViewById(R.id.doca2);
        doca3 = findViewById(R.id.doca3);
        doca4 = findViewById(R.id.doca4);

        //

        //Configura número minimo e maximo de etiquetas
        numeroEtiqueta.setMinValue(1);
        numeroEtiqueta.setMaxValue(50);
        //

        botaoImprime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    imprime();
                } catch (ZebraPrintException e) {
                    e.printStackTrace();
                }

            }
        });

        boxButton.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick(View view) {

                     try {
                         Intent intent = new Intent(getApplicationContext(), Box.class);
                         startActivity(intent);
                     }
                     catch (Exception e){}
                 }
             }

        );

        //Executa o imprime para imprimir as etiquetas


    }

    @SuppressLint("DefaultLocale")
    private void imprime() throws ZebraPrintException {

        Zebra zebra = new Zebra();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int selectedId = tipo.getCheckedRadioButtonId();
        tipoSelect = findViewById(selectedId);

        BuscaRazao buscaRazao = new BuscaRazao();

        boolean doca1Checked = doca1.isChecked();
        boolean doca2Checked = doca2.isChecked();
        boolean doca3Checked = doca3.isChecked();
        boolean doca4Checked = doca4.isChecked();

        boolean existe = buscaRazao.verifica(manifesto.getText().toString(), codCliente.getText().toString());
        System.out.println(existe);
        if (existe){
            textView.setVisibility(View.INVISIBLE);
            String razao = buscaRazao.retornaRazao(manifesto.getText().toString(), codCliente.getText().toString());
            for (int i = 0; i < numeroEtiqueta.getValue(); i++)
            {
                if (doca1Checked)
                {
                    zebra.imprimeEtiquetas(manifesto.getText().toString(), codCliente.getText().toString(), razao, String.format("%02d", numeroEtiqueta.getValue()), String.format("%02d", i+1), tipoSelect.getText().toString(), dtf.format(LocalDateTime.now()), 1);
                }
                if(doca2Checked)
                {
                    zebra.imprimeEtiquetas(manifesto.getText().toString(), codCliente.getText().toString(), razao, String.format("%02d", numeroEtiqueta.getValue()), String.format("%02d", i+1), tipoSelect.getText().toString(), dtf.format(LocalDateTime.now()), 2);
                }
                if (doca3Checked)
                {
                    zebra.imprimeEtiquetas(manifesto.getText().toString(), codCliente.getText().toString(), razao, String.format("%02d", numeroEtiqueta.getValue()), String.format("%02d", i+1), tipoSelect.getText().toString(), dtf.format(LocalDateTime.now()), 3);
                }
                if (doca4Checked)
                {
                    zebra.imprimeEtiquetas(manifesto.getText().toString(), codCliente.getText().toString(), razao, String.format("%02d", numeroEtiqueta.getValue()), String.format("%02d", i+1), tipoSelect.getText().toString(), dtf.format(LocalDateTime.now()), 4);
                }
            }
        }

        else{
            textView.setVisibility(View.VISIBLE);
        }

    }
}

