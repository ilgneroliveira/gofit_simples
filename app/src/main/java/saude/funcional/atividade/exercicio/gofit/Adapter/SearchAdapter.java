package saude.funcional.atividade.exercicio.gofit.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import saude.funcional.atividade.exercicio.gofit.Model.Exercise;
import saude.funcional.atividade.exercicio.gofit.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Exercise> exercises = null;
    private List<ImageView> images;
    private int i = 0;

    public SearchAdapter(Context context,  List<Exercise> exercises) {
        super();
        this.exercises = exercises;
        this.context = context;
        images = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Exercise exercise = exercises.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_list_search, null);

        SmartImageView tvSearchimage = view.findViewById(R.id.ivSearchImage);
        tvSearchimage.setImageUrl(exercise.getFeatured_image_url());
        tvSearchimage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView tvSearchTitle = view.findViewById(R.id.tvSearchTitle);
        tvSearchTitle.setText(exercise.getTitle());

        return view;
    }
}