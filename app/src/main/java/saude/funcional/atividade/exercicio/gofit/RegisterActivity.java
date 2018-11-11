package saude.funcional.atividade.exercicio.gofit;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

import saude.funcional.atividade.exercicio.gofit.Control.UserControl;
import saude.funcional.atividade.exercicio.gofit.Model.User;

public class RegisterActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    Uri selectedImageUri;
    ImageView bgRegister;
    ImageView imagePerfil;
    ProgressDialog dialog;
    EditText edEmail;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bgRegister = findViewById(R.id.bg_register);

        BitmapDrawable drawable = (BitmapDrawable) bgRegister.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap blurred = blurRenderScript(bitmap);//second parametre is radius
        bgRegister.setImageBitmap(blurred);

//        imagePerfil = findViewById(R.id.imagePerfil);
//        imagePerfil.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View arg0) {
//
//                // in onCreate or any event where your want the user to
//                // select a file
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,
//                        "Selecionar Imagem"), SELECT_PICTURE);
//            }
//        });

        Button button = findViewById(R.id.btSignup);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog = ProgressDialog.show(RegisterActivity.this, "Salvando", "Aguarde...");
                User user = createUser();
                UserControl.saveUser(user, getApplicationContext(), true,dialog,edEmail);
            }
        });
    }

    private User createUser() {
        EditText edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        EditText edPassword = findViewById(R.id.edPassword);
        EditText edDate = findViewById(R.id.edDate);
        Spinner sexo = findViewById(R.id.sexo);
//        Spinner available_time = findViewById(R.id.available_time);
        EditText edWeight = findViewById(R.id.edWeight);
        EditText edHeight = findViewById(R.id.edHeight);

        User user = new User();
        user.setName(edName.getText().toString());
        user.setEmail(edEmail.getText().toString());
        user.setPassword(edPassword.getText().toString());
        user.setBirthDate(edDate.getText().toString());
        user.setKind(parseKind(sexo.getSelectedItem().toString()));
//        user.setAvailable_time(parseAvailableTime(available_time.getSelectedItem().toString()));
        user.setWeight(edWeight.getText().toString());
        user.setHeight(edHeight.getText().toString());

//        user.setImage(getPath(selectedImageUri));

        return user;
    }

    private String parseAvailableTime(String s) {
        Map<String, String> kinds = new HashMap<>();
        kinds.put("de 0 a 10 minutos", "1");
        kinds.put("de 10 a 30 minutos", "2");
        kinds.put("de 30 a 60 minutos", "3");
        kinds.put("60 minutos ou mais", "4");

        return kinds.get(s);
    }

    private String parseKind(String s) {
        Map<String, String> kinds = new HashMap<>();
        kinds.put("Masculino", "male");
        kinds.put("Feminino", "famele");

        return kinds.get(s);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                imagePerfil.setImageURI(selectedImageUri);
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Login.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() { //Botão BACK padrão do android
        startActivity(new Intent(this, Login.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
    }

    @SuppressLint("NewApi")
    private Bitmap blurRenderScript(Bitmap smallBitmap) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(RegisterActivity.this);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(25); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private Bitmap RGB565toARGB888(Bitmap img) {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }
}
