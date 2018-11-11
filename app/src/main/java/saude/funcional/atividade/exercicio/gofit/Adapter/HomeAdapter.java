package saude.funcional.atividade.exercicio.gofit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;

import saude.funcional.atividade.exercicio.gofit.Model.Exercise;
import saude.funcional.atividade.exercicio.gofit.Model.ExerciseRecommedation;
import saude.funcional.atividade.exercicio.gofit.R;

public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<ExerciseRecommedation> exercises = null;
    private List<ImageView> images;
    private int i = 0;

    public HomeAdapter(Context context, List<ExerciseRecommedation> exercises) {
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
        ExerciseRecommedation exercise_recommedation = exercises.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_list_search, null);

        SmartImageView tvSearchimage = view.findViewById(R.id.ivSearchImage);
        tvSearchimage.setImageUrl(exercise_recommedation.getExercise().getFeaturedImageUrl());
        tvSearchimage.setDrawingCacheEnabled(true);

//        images.add(tvSearchimage);

//        new SearchAdapter.DownloadImagemAsyncTask().execute(exercise.getFeatured_image_url(), i + "");
//        i++;

        TextView tvSearchTitle = view.findViewById(R.id.tvSearchTitle);
        tvSearchTitle.setText(exercise_recommedation.getExercise().getTitle());

        return view;
    }

//    private class DownloadImagemAsyncTask extends AsyncTask<String, Void, Bitmap> {
//        private ImageView imagem_async;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            imagem_async = images.get(Integer.parseInt(params[1]));
//            try {
//                URL url = new URL(params[0]);
//                HttpURLConnection conexao = (HttpURLConnection)
//                        url.openConnection();
//                conexao.setRequestMethod("GET");
//                conexao.setDoInput(true);
//                conexao.connect();
//
//                InputStream is = conexao.getInputStream();
//                return BitmapFactory.decodeStream(is);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            super.onPostExecute(result);
//            if (result != null) {
//                imagem_async.setImageBitmap(result);
//            }
//        }
//    }
}