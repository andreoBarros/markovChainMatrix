package com.example.myapplication;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import org.ejml.simple.SimpleMatrix;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class MainActivity extends AppCompatActivity {

    int numLinhas, numColunas, numGeracoes, numEstados;
    boolean tableExiste = false;
    EditText LinhasInput, ColunasInput, GeracoesInput, EstadosInput;

    Button tableSize;
    Button tableCalc;
    Button initialState;

    boolean hasInitialState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinhasInput = (EditText) findViewById(R.id.numLinhas);
        ColunasInput = (EditText) findViewById(R.id.numColunas);
        GeracoesInput = (EditText) findViewById(R.id.geracao);
        EstadosInput = findViewById(R.id.estados);

        tableSize = (Button) findViewById(R.id.buttonTableSize);
        tableCalc = findViewById(R.id.buttonCalcRess);
        initialState = (Button) findViewById(R.id.buttonIniStates);

        initialState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numEstados = Integer.valueOf(EstadosInput.getText().toString());
                initVector(numEstados);
            }
        });

        tableSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numLinhas = Integer.valueOf(LinhasInput.getText().toString());
                numColunas = Integer.valueOf(ColunasInput.getText().toString());
                initTable(numLinhas, numColunas);
                //showToast(String.valueOf(numColunas));
            }
        });

        tableCalc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleMatrix tabelaInicial = MontarAtabela(numColunas, numLinhas);
                    if (!hasInitialState){
                        numGeracoes = Integer.valueOf(GeracoesInput.getText().toString());
                        printAtabela(MultiplicaTabela(tabelaInicial, numGeracoes));
                    }
                    else{
                        printAtabela(tabelaInicial);
                    }
                }
        });



        {
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            //NavigationUI.setupWithNavController(navView, navController);
        }
    }

    private void showToast(String text){
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void initVector(int numEstados){
        //table
        TableLayout lvector = findViewById(R.id.vetorInicial);
        //monta a tabela para o usuario colocar a entrada

            TableRow vec= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            vec.setLayoutParams(lp);
            for (int j = 0; j < numEstados; j++) {
                EditText num = new EditText(this);
                num.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                String pos = "00"  + j;
                num.setId(Integer.valueOf(pos));
                num.setWidth(100);
                vec.addView(num);
            }
            lvector.addView(vec);

        //end table
    }

    public SimpleMatrix MontarOvetor(int numLinhas, int numColunas){

        numLinhas = 1;
        SimpleMatrix vetorInicial = new SimpleMatrix(numLinhas,numColunas);
        double numeroFinal;
        EditText numInput;

        //faz a tabela a partir da entrada do usuário
        for (int i = 0; i < numLinhas; i++)
        {
            for (int j = 0; j < numColunas; j++)
            {
                String pos = "00" + j;
                int posicao = getResources().getIdentifier(pos,
                        "id", getPackageName());

                numInput = (EditText) findViewById(posicao);
                numeroFinal = Double.valueOf(numInput.getText().toString());
                vetorInicial.set(i,j,numeroFinal);
            }
        }
        return vetorInicial;
    }


    public void initTable(double linhas, double colunas){
        //table
        if(tableExiste){

        }

        TableLayout ll = findViewById(R.id.table);
        tableExiste = true;
        tableCalc.setVisibility(View.VISIBLE);
        //monta a tabela para o usuario colocar a entrada
        for (int i = 0; i < linhas; i++) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);
            for (int j = 0; j < colunas; j++) {
                EditText num = new EditText(this);
                num.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                String pos = "" + i + j;
                num.setId(Integer.valueOf(pos));
                num.setWidth(100);
                row.addView(num);
            }
            ll.addView(row, i);
        }

        //end table
    }

    public SimpleMatrix MontarAtabela(int numLinhas, int numColunas){

        SimpleMatrix tabelaDoUsuario = new SimpleMatrix(numLinhas,numColunas);
        double numeroFinal;
        EditText numInput;

        //faz a tabela a partir da entrada do usuário
        for (int i = 0; i < numLinhas; i++)
        {
            for (int j = 0; j < numColunas; j++)
            {
                String pos = "" + i + j;
                int posicao = getResources().getIdentifier(pos,
                        "id", getPackageName());

                numInput = (EditText) findViewById(posicao);
                numeroFinal = Double.valueOf(numInput.getText().toString());
                tabelaDoUsuario.set(i,j,numeroFinal);
            }
        }
        return tabelaDoUsuario;
    }

    public void printAtabela(SimpleMatrix tabela) {


        //Alimenta a matriz com valores aleatórios
                TableLayout ll = findViewById(R.id.table);
                for (int i = 0; i < numLinhas; i++) {

                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                    row.setLayoutParams(lp);

                    for (int j = 0; j < numColunas; j++) {
                        EditText num = new EditText(this);
                        num.setInputType(TYPE_CLASS_NUMBER);
                        num.setInputType(TYPE_CLASS_NUMBER);
                        String pos = "" + i + j;
                        num.setId(Integer.valueOf(pos));
                        num.setText(String.valueOf(tabela.get(i,j)));
                        num.setWidth(100);
                        row.addView(num);
                    }

                    ll.addView(row, i);
                }

    }

    public SimpleMatrix MultiplicaTabela(SimpleMatrix tabela, int geracao){

        SimpleMatrix tabelaMultiplicada = tabela;
        //essa variavel é placehold para o dia que tiver como escolher a geração pela interface

            for (int i = 1; i < geracao; i++) {
                tabela = tabela.mult(tabelaMultiplicada);
            }
                return tabela;


    }



}

