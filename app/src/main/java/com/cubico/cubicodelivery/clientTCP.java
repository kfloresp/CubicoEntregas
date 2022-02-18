package com.cubico.cubicodelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cubico.cubicodelivery.helper.TCPClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class clientTCP extends AppCompatActivity {
    EditText editText;
    TextView txtRecibo;


    String mensaje="";
    String returndata="";

    private TCPClient mTcpClient = null;
    private connectTask conctTask = null;
    private String ipAddressOfServerDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_tcp);
        editText=findViewById(R.id.editText);
        txtRecibo=findViewById(R.id.txtRecibe);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitNetwork().build());
        mTcpClient = null;
        ipAddressOfServerDevice="172.16.32.3";
        // connect to the server
        conctTask = new connectTask();
        conctTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void send_text(View v){
        String message= editText.getText().toString();
      //  log(" socket " + ip + " " + puerto);
        if (mTcpClient != null)
        {
            mTcpClient.sendMessage(message);
        }
    }
    /* private void ejecutaCliente() {
         String ip = "172.16.32.3";
         int puerto = 5000;
         log(" socket " + ip + " " + puerto);
         try {
             Socket sk = new Socket(ip, puerto);
             BufferedReader entrada = new BufferedReader(
                     new InputStreamReader(sk.getInputStream()));
             PrintWriter salida = new PrintWriter(
                     new OutputStreamWriter(sk.getOutputStream()), true);
             log("enviando... Hola Mundo ");
             salida.println("Hola Mundo");
             log("recibiendo ... " + entrada.readLine());
             sk.close();
         } catch (Exception e) {
             log("error: " + e.toString());
         }
     }*/
    private void log(String string) {
        txtRecibo.append(string + "\n");
    }
    /*receive the message from server with asyncTask*/
    public class connectTask extends AsyncTask<String,String,TCPClient> {
        @Override
        protected TCPClient doInBackground(String... message)
        {
            //create a TCPClient object and
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived()
            {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message)
                {
                    try
                    {
                        //this method calls the onProgressUpdate
                        publishProgress(message);
                        if(message!=null)
                        {
                            System.out.println("Return Message from Socket::::: >>>>> "+message);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            },ipAddressOfServerDevice);
            mTcpClient.run();
            if(mTcpClient!=null)
            {
                mTcpClient.sendMessage("Initial Message when connected with Socket Server");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
          //  arrayList.add(values[0]);
            Log.i("TCP RECIBE", values[0]);
            txtRecibo.setText(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            //mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy()
    {
        try
        {
            System.out.println("onDestroy.");
            mTcpClient.sendMessage("bye");
            mTcpClient.stopClient();
            conctTask.cancel(true);
            conctTask = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
