package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbSymbian";
    private static final int DB_VERSION = 2;
    private static SQLHelper INSTANCE;

    public static SQLHelper getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new SQLHelper(context);
        }

        return INSTANCE;
    }

    public SQLHelper(@Nullable Context context) {
        /** 'SUPER' INDICA O CONTEXTO PARA A CLASSE-MÃE, AO CONTRÁRIO DO 'THIS' QUE INDICA QUE SERÁ LOCAL **/
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE tblUsuario" +
                "(idUsuario INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "sobrenome TEXT," +
                "login TEXT," +
                "senha TEXT)");

        Log.d("SQLITE-", "BANCO DE DADOS CRIADO! - " + DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("CREATE TABLE tblEndereco" +
                "(idEndereco INTEGER PRIMARY KEY," +
                "idUsuario INTEGER," +
                "cep TEXT," +
                "numero TEXT," +
                "complemento TEXT," + "FOREIGN KEY (idUsuario) REFERENCES tblUsuario(idUsuario))");

        Log.d("SQLITE-", "BANCO DE DADOS CRIADO! - " + DB_VERSION);
    }

    public int addUser(String nome, String sobrenome, String login, String senha) {

        //Configura o SQLITE para escrita:
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try {

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("nome", nome);
            values.put("sobrenome", sobrenome);
            values.put("login", login);
            values.put("senha", senha);

            int idUsuario = (int) sqLiteDatabase.insertOrThrow("tblUsuario", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return idUsuario;

        } catch (Exception e) {

            Log.d("SQLITE-", e.getMessage());
            return 0;

        } finally {

            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }

        }

    }

    /**
     * INSERÇÃO DE ENDEREÇO
     **/
    public boolean addAdress(int idUsuario, String cep, String numero, String complemento) {

        //Configura o SQLITE para escrita:
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int idEndereco = 0;

        try {

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("idUsuario", idUsuario);
            values.put("cep", cep);
            values.put("numero", numero);
            values.put("complemento", complemento);

            sqLiteDatabase.insertOrThrow("tblEndereco", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;

        } catch (Exception e) {

            Log.d("SQLITE-", e.getMessage());
            return false;

        } finally {

            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }

        }


    }
}
