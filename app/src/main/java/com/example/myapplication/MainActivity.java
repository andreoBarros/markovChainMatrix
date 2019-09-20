package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class MainActivity extends AppCompatActivity {

    int numLinhas, numColunas;
    boolean tableExiste = false;
    EditText LinhasInput, ColunasInput;
    Button tableSize;
    Button tableCalc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinhasInput = (EditText) findViewById(R.id.numLinhas);
        ColunasInput = (EditText) findViewById(R.id.numColunas);

        tableSize = (Button) findViewById(R.id.buttonTableSize);
        tableSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numLinhas = Integer.valueOf(LinhasInput.getText().toString());
                numColunas = Integer.valueOf(ColunasInput.getText().toString());
                initTable(numLinhas, numColunas);
                //showToast(String.valueOf(numColunas));
            }
        });

        tableCalc = findViewById(R.id.buttonCalcRess);
        tableCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[][] tabelaInicial = MontarAtabela(numColunas, numLinhas);
                printAtabela(tabelaInicial);
                //showToast(String.valueOf(tabelaInicial[0][0]));

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

    public void initTable(int linhas, int colunas){
        //table
        if(tableExiste){

        }

        TableLayout ll = findViewById(R.id.table);
        tableExiste = true;
        tableCalc.setVisibility(View.VISIBLE);

        for (int i = 0; i < linhas; i++) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);
            for (int j = 0; j < colunas; j++) {
                EditText num = new EditText(this);
                num.setInputType(TYPE_CLASS_NUMBER);
                String pos = "" + i + j;
                num.setId(Integer.valueOf(pos));
                num.setWidth(100);
                row.addView(num);
            }
            ll.addView(row, i);
        }

        //end table
    }

    public int[][] MontarAtabela(int numLinhas, int numColunas){

        int[][] tabelaDoUsuario = new int[numLinhas][numColunas];
        int numeroFinal;
        EditText numInput;

        //Alimenta a matriz com valores aleatórios
        for (int i = 0; i < numLinhas; i++)
        {
            for (int j = 0; j < numColunas; j++)
            {
                String pos = "" + i + j;
                int posicao = getResources().getIdentifier(pos,
                        "id", getPackageName());

                numInput = (EditText) findViewById(posicao);
                numeroFinal = Integer.valueOf(numInput.getText().toString());

                tabelaDoUsuario[i][j] = numeroFinal;
            }
        }
        return tabelaDoUsuario;
    }

    public void printAtabela(int[][] tabela) {


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
                        num.setText(String.valueOf(tabela[i][j]));
                        num.setWidth(100);
                        row.addView(num);
                    }

                    ll.addView(row, i);
                }

    }
}
