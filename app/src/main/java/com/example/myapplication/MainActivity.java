package com.example.myapplication;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    Button multiplicaTransicaoInicial;
    Button matrixIteration;

    SimpleMatrix estadoInicial = SimpleMatrix.identity('0');

    private boolean hasInitialState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // uma porrada de variáveis
        {
            LinhasInput = findViewById(R.id.numLinhas);
            ColunasInput = findViewById(R.id.numColunas);
            GeracoesInput = findViewById(R.id.geracao);
            EstadosInput = findViewById(R.id.estados);

            tableSize = findViewById(R.id.buttonTableSize);
            tableCalc = findViewById(R.id.buttonCalcRess);
            initialState = findViewById(R.id.buttonIniStates);
            multiplicaTransicaoInicial = findViewById(R.id.buttonMultMatriz);
            matrixIteration = findViewById(R.id.buttonShowGeneration);
        }

        initialState.setOnClickListener((View v) ->{
                numEstados = Integer.valueOf(EstadosInput.getText().toString());
                initVector(numEstados);

        });

        tableSize.setOnClickListener((View v) -> {
                numLinhas = Integer.valueOf(LinhasInput.getText().toString());
                numColunas = Integer.valueOf(ColunasInput.getText().toString());
                initTable(numLinhas, numColunas);
                //showToast(String.valueOf(numColunas));
        });

        tableCalc.setOnClickListener((View v) ->{
                    SimpleMatrix tabelaInicial = MontarAtabela(numColunas, numLinhas);
                    numGeracoes = Integer.valueOf(GeracoesInput.getText().toString());
                    printVetor(estadoInicial.mult(MultiplicaTabela(tabelaInicial, numGeracoes)));
        });

        multiplicaTransicaoInicial.setOnClickListener((View v) ->{
                estadoInicial = MontarOvetor(numEstados);
                //printVetor(estadoInicial);
        });

        matrixIteration.setOnClickListener((View v) ->{
            SimpleMatrix tabelaInicial = MontarAtabela(numColunas, numLinhas);
            numGeracoes = Integer.valueOf(GeracoesInput.getText().toString());
             //   printAtabela(tabelaInicial);
            printAtabela((MultiplicaTabela(tabelaInicial, numGeracoes)));

        });

        {
//            BottomNavigationView navView = findViewById(R.id.nav_view);
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
//--------------------------------------------------------------------------------------
//    private void showToast(String text){
//        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
//    }
//--------------------------------------------------------------------------------------
    public void initVector(int numStates){
        //table
        TableLayout lvector = findViewById(R.id.vetorInicial);

        lvector.removeAllViews();

        multiplicaTransicaoInicial.setVisibility(View.VISIBLE);
        //monta a tabela para o usuario colocar a entrada

            TableRow vec= new TableRow(this);


            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            vec.setLayoutParams(lp);
            for (int j = 0; j < numStates; j++) {
                EditText num = new EditText(this);
                num.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                String pos = "10"  + j;
                num.setId(Integer.valueOf(pos));
                num.setWidth(100);
                vec.addView(num);
            }
            lvector.addView(vec);

        //end table
    }
//--------------------------------------------------------------------------------------
    public SimpleMatrix MontarOvetor(int numColumns){

        int numLinhas = 1;
        SimpleMatrix vetorInicial = new SimpleMatrix(numLinhas,numColumns);
        double numeroFinal;
        EditText numInput;

        hasInitialState = true;
        if(hasInitialState && tableExiste){
            tableCalc.setVisibility(View.VISIBLE);
        }
        //faz a tabela a partir da entrada do usuário
        for (int i = 0; i < numLinhas; i++)
        {
            for (int j = 0; j < numColumns; j++)
            {
                String pos = "10" + j;
                int posicao = getResources().getIdentifier(pos,
                        "id", getPackageName());

                numInput = findViewById(posicao);

                if(numInput.getText().toString().equals("")){
                    numeroFinal = 0;
                }
                else {
                    numeroFinal = Double.valueOf(numInput.getText().toString());
                }

                vetorInicial.set(i,j,numeroFinal);
            }
        }
        hasInitialState = true;
        return vetorInicial;
    }
//--------------------------------------------------------------------------------------
    public void printVetor(SimpleMatrix matrix) {


        //Alimenta a matriz com valores aleatórios
        TableLayout lvector = findViewById(R.id.tableOutput);

        for (int i = 0; i < matrix.numRows(); i++) {
            TableRow vec = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            vec.setLayoutParams(lp);

            for (int j = 0; j < matrix.numCols(); j++) {
                TextView num = new TextView(this);
                num.setInputType(TYPE_CLASS_NUMBER);
                num.setInputType(TYPE_CLASS_NUMBER);
                String pos = "00" + j;
                num.setId(Integer.valueOf(pos));
                num.setText(String.valueOf(matrix.get(j)));
                num.setWidth(100);
                num.setPadding(5,15,5,5);
                vec.addView(num);
            }

            lvector.addView(vec);
        }

    }
//--------------------------------------------------------------------------------------
    public void initTable(double lines, double columns){

        TableLayout ll = findViewById(R.id.tableInput);

        ll.removeAllViews();
        tableExiste = true;

        findViewById(R.id.geracao).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonShowGeneration).setVisibility(View.VISIBLE);
        if(hasInitialState && tableExiste){
            tableCalc.setVisibility(View.VISIBLE);
        }
        //monta a tabela para o usuario colocar a entrada
        for (int i = 0; i < lines; i++) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);
            for (int j = 0; j < columns; j++) {
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
//--------------------------------------------------------------------------------------
    public SimpleMatrix MontarAtabela(int numLines, int numColumns){

        SimpleMatrix tabelaDoUsuario = new SimpleMatrix(numLines,numColumns);
        double numeroFinal;
        EditText numInput;

        //faz a tabela a partir da entrada do usuário
        for (int i = 0; i < numLines; i++)
        {
            for (int j = 0; j < numColumns; j++)
            {
                String pos = "" + i + j;
                int posicao = getResources().getIdentifier(pos,
                        "id", getPackageName());

                numInput = findViewById(posicao);
                if(numInput.getText().toString().equals("")){
                    numeroFinal = 0;
                }
                else {
                    numeroFinal = Double.valueOf(numInput.getText().toString());
                }
                tabelaDoUsuario.set(i,j,numeroFinal);
            }
        }
        return tabelaDoUsuario;
    }
//--------------------------------------------------------------------------------------
    public void printAtabela(SimpleMatrix matrix) {


        //Alimenta a matriz com valores aleatórios
                TableLayout ll = findViewById(R.id.tableOutput);
                for (int i = 0; i < matrix.numRows(); i++) {

                    TableRow row= new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                    row.setLayoutParams(lp);

                    for (int j = 0; j < matrix.numRows(); j++) {
                        TextView num = new TextView(this);
                        num.setInputType(TYPE_CLASS_NUMBER);
                        num.setInputType(TYPE_CLASS_NUMBER);
                        String pos = "" + i + j;
                        num.setId(Integer.valueOf(pos));
                        num.setText(String.valueOf(matrix.get(i,j)));
                        num.setWidth(100);
                        num.setPadding(5,5,5,5);
                        row.addView(num);
                    }

                    ll.addView(row, i);
                }

    }
//--------------------------------------------------------------------------------------
    public SimpleMatrix MultiplicaTabela(SimpleMatrix matrix, int generation){

        SimpleMatrix tabelaMultiplicada = matrix;
        //essa variavel é placehold para o dia que tiver como escolher a geração pela interface

            for (int i = 1; i < generation; i++) {
                matrix = matrix.mult(tabelaMultiplicada);
            }
                return matrix;


    }
//--------------------------------------------------------------------------------------


}

