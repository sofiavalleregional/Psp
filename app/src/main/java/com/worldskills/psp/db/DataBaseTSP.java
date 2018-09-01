package com.worldskills.psp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

public class DataBaseTSP extends SQLiteOpenHelper {

    private static final String NOMBRE="databasetsp.db";
    private static final int VERSION=1;
    private Context context;


    private static final String TABLA_PROYECTOS="CREATE TABLE PROYECTOS(" +
            "IDPROYEC INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NOMPROYEC TEXT," +
            "TIEMPOTOTAL TEXT)";


    private static final String TABLA_TIMELOG="CREATE TABLE TIMELOG(" +
            "IDTIMELOG INTEGER PRIMARY KEY AUTOINCREMENT," +
            "IDPROYECT INTEGER," +
            "FASE TEXT," +
            "FECHAINICIO TEXT," +
            "FECHAFINAL TEXT," +
            "DELTA INTEGER," +
            "COMMENTS TEXT," +
            "FOREIGN KEY (IDPROYECT) REFERENCES PROYECTOS (IDPROYEC))";


    private static final String TABLA_DEFECLOG="CREATE TABLE DEFELOG(" +
            "IDDEFECLOG INTEGER PRIMARY KEY AUTOINCREMENT," +
            "IDPROYECT INTEGER," +
            "TYPE TEXT," +
            "FECHA TEXT," +
            "INJECTED TEXT," +
            "REMOVED TEXT," +
            "FIXTIME INTEGER," +
            "DESCRIPCION TEXT," +
            "FOREIGN KEY (IDPROYECT) REFERENCES PROYECTOS (IDPROYEC))";


    /*Metodo constructor para iniciar la base de dato y al mismo modo acceder a todos sus metodos para controlar la base de datos*/
    public DataBaseTSP(Context context){
        super(context,NOMBRE,null,VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_PROYECTOS);
        db.execSQL(TABLA_TIMELOG);
        db.execSQL(TABLA_DEFECLOG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CREATE"+TABLA_PROYECTOS);
        db.execSQL("DROP TABLE IF EXISTS CREATE"+TABLA_TIMELOG);
        db.execSQL("DROP TABLE IF EXISTS CREATE"+TABLA_DEFECLOG);
    }

    /*Metodo que se encarga de gestionar el control completo de la tabla de proyecto, ya sea para guardar, consultar o actualizar
    * si es necesario este metodo devolvera un objeto cursor para ser utilizado
    * */
    public static final int GUARDAR=0;
    public static final int CARGAR_PROYECTOS=1;
    public static final int CARGAR_UN_PROYECTO=2;
    public static final int ACTUALIZAR_PROYECTO=3;

    public Cursor tablaProyectos(int accion, int idProyecto, String nombre, int tiempo){
        SQLiteDatabase db;
        Cursor cursor=null;
        ContentValues valores=new ContentValues();

        switch (accion){
            case GUARDAR:
                db=getWritableDatabase();
                valores.put("NOMPROYEC",nombre);
                valores.put("TIEMPOTOTAL",tiempo);

                db.insert("PROYECTOS",null,valores);
                Toast.makeText(context, "Proyecto guardado", Toast.LENGTH_SHORT).show();

                break;
            case CARGAR_PROYECTOS:
                db=getReadableDatabase();
                try{
                    String orderBy="IDPROYEC DESC";
                    cursor=db.query("PROYECTOS",null,null,null,null,null,orderBy);
                    Toast.makeText(context, "Proyectos cargados", Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
                break;
            case CARGAR_UN_PROYECTO:
                db=getReadableDatabase();
                try{
                    String selection="IDPROYEC"+" = ? ";
                    String[] selectionArg={idProyecto+""};
                    cursor=db.query("PROYECTOS",null,selection,selectionArg,null,null,null);
                    Toast.makeText(context, "Proyecto ID:"+idProyecto, Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
                break;
            case ACTUALIZAR_PROYECTO:
                db=getWritableDatabase();
                valores.put("TIEMPOTOTAL",tiempo);
                String where="IDPROYEC" +" lIKE ?";
                String[] whereArgs={idProyecto+""};
                db.update("PROYECTOS",valores,where,whereArgs);
                Toast.makeText(context, "Proyecto actualizado", Toast.LENGTH_SHORT).show();

                break;
        }

        return cursor;
    }


    /*Metodo con el fin de cargar los datos de la tabla timelog la onsulta depende de los parametros que eset reciba*/
    public Cursor cargarTimelog(String idProyecto, String fase){
        Cursor cursor=null;
        SQLiteDatabase db=getReadableDatabase();


            try{
                String[] columns={"DELTA"};
                String selection="IDPROYECT" + " = ? " + " AND " + "FASE" + " = ? ";
                String[] selectionArgs={idProyecto, fase};
                cursor=db.query("TIMELOG",columns,selection,selectionArgs,null,null,null);
                Toast.makeText(context, "Load timeLog", Toast.LENGTH_SHORT).show();

            }catch (Exception e){}





        return cursor;
    }


    /*Metodo para guardar los datos de la table del timelog*/
    public void saveTimeLog(int idProyecto, String fase, String fechaI, String fechaF, int delta, String comments){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("IDPROYECT",idProyecto);
        valores.put("FASE",fase);
        valores.put("FECHAINICIO",fechaI);
        valores.put("FECHAFINAL",fechaF);
        valores.put("DELTA",delta);
        valores.put("COMMENTS",comments);

        db.insert("TIMELOG",null,valores);
        Toast.makeText(context, "TimeLog guardado", Toast.LENGTH_SHORT).show();

    }


    /*Metodo para cargar todos los datos de la tabla de defect log dependiendo de los parametro que reciba*/
    public Cursor cargarDefecLog(String idProyecto, String injected, String removed){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=null;

        try {


            if (injected!=null){

                String selection="IDPROYECT" + " = ? "+ " AND "+ "INJECTED" + " = ?";
                String[] selectionArgs={idProyecto,injected};
                cursor=db.query("DEFELOG",null,selection,selectionArgs,null,null,null);
                Toast.makeText(context, "Load "+injected, Toast.LENGTH_SHORT).show();

            }else if (removed!=null){
                String selection="IDPROYECT" + " = ? "+ " AND "+ "REMOVED" + " = ?";
                String[] selectionArgs={idProyecto,removed};
                cursor=db.query("DEFELOG",null,selection,selectionArgs,null,null,null);
                Toast.makeText(context, "Load "+removed, Toast.LENGTH_SHORT).show();

            }else{
                String selection="IDPROYECT" + " = ?";
                String[] selectionArgs={idProyecto};
                cursor=db.query("DEFELOG",null,selection,selectionArgs,null,null,null);
                Toast.makeText(context, "Defectos cargados", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){}


        return cursor;

    }

    /*Metodo para guardar los datos de la table del defectlog*/
    public void saveDefecLog(int idProyecto, String type, String fecha, String injected, String removed, int fixTime, String descripcion){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues valores=new ContentValues();

        valores.put("IDPROYECT",idProyecto);
        valores.put("TYPE",type);
        valores.put("FECHA",fecha);
        valores.put("INJECTED",injected);
        valores.put("REMOVED",removed);
        valores.put("FIXTIME",fixTime);
        valores.put("DESCRIPCION",descripcion);

        db.insert("DEFELOG",null,valores);
        Toast.makeText(context, "DefectLog guardado", Toast.LENGTH_SHORT).show();
    }
}
