package saude.funcional.atividade.exercicio.gofit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import java.util.List;

import saude.funcional.atividade.exercicio.gofit.Model.ExerciseRecommedation;
import saude.funcional.atividade.exercicio.gofit.R;
import saude.funcional.atividade.exercicio.gofit.ShowExersiceActivity;

public class StartAdapter extends RecyclerView.Adapter<StartAdapter.GroceryViewHolder> {
    private List<ExerciseRecommedation> horizontalGrocderyList;
    Context context;

    public StartAdapter(List<ExerciseRecommedation> horizontalGrocderyList, Context context) {
        this.horizontalGrocderyList = horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        holder.imageView.setImageUrl(horizontalGrocderyList.get(position).getExercise().getFeaturedImageUrl());
        holder.txtview.setText(horizontalGrocderyList.get(position).getExercise().getTitle());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowExersiceActivity.class);
                intent.putExtra("id", horizontalGrocderyList.get(position).getExercise().getId());
                context.startActivity(intent);
            }
        });
        holder.txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowExersiceActivity.class);
                intent.putExtra("id", horizontalGrocderyList.get(position).getExercise().getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalGrocderyList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        SmartImageView imageView;
        TextView txtview;

        public GroceryViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.idImage);
            txtview = view.findViewById(R.id.idName);
        }
    }
}