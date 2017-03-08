package br.com.soulskyye.tecnonutri.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import retrofit2.Call;

/**
 * Created by ulissescurti on 3/7/17.
 */

public class Utils {

    private static ProgressDialog progressDialog;

    public static String getFormattedDate(String date){// yyyy-MM-dd
        String[] splittedDate = date.split("-");
        return splittedDate[2]+"/"+splittedDate[1]+"/"+splittedDate[0];
    }

    public static void showProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog(){
        progressDialog.dismiss();
    }

    public static void showErrorPopup(Context context, Throwable t){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Ocorreu algum erro, nos desculpe. Se o erro persistir entre em contato conosco.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Entendido",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
