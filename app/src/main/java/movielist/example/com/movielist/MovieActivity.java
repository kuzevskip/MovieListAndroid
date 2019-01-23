package movielist.example.com.movielist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import movielist.example.com.movielist.model.Actor;
import movielist.example.com.movielist.model.MovieController;
import movielist.example.com.movielist.model.MovieDetail;

public class MovieActivity extends AppCompatActivity {

    public static final String TAG = MovieActivity.class.getSimpleName();

    private RecyclerView actorsRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView movieTitle;
    private TextView description;
    private TextView rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        actorsRecyclerView = findViewById(R.id.actorsList);

        layoutManager = new LinearLayoutManager(this);
        actorsRecyclerView.setLayoutManager(layoutManager);

        movieTitle = findViewById(R.id.movieTitleTextView);
        description = findViewById(R.id.descriptionTextView);
        rating = findViewById(R.id.ratingTextView);

        String movieName = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movieName = extras.getString(Constants.MOVIE_NAME);
        }
        movieTitle.setText(movieName);
        MovieDetail movieDetail = MovieController.getMovieDetails(movieName);
        description.setText(movieDetail.getDescription());
        rating.setText(String.valueOf(movieDetail.getScore()));

        adapter = new MovieActivity.ActorsAdapter(movieName);
        actorsRecyclerView.setAdapter(adapter);
    }

    public class ActorsAdapter extends RecyclerView.Adapter<MovieActivity.ActorsAdapter.ActorViewHolder> {
        private String movieName;

        public ActorsAdapter(String movieName) {
            this.movieName = movieName;
        }

        class ActorViewHolder extends RecyclerView.ViewHolder {
            ImageView picture;
            TextView name;
            TextView age;


            ActorViewHolder(View v) {
                super(v);
                picture = v.findViewById(R.id.pictureImageView);
                name = v.findViewById(R.id.nameTextView);
                age = v.findViewById(R.id.ageTextView);
            }
        }

        @Override
        public MovieActivity.ActorsAdapter.ActorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_item, parent, false);
            return new MovieActivity.ActorsAdapter.ActorViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MovieActivity.ActorsAdapter.ActorViewHolder holder, int position) {
            Actor actor = MovieController.getMovieActors(movieName)[position];
            holder.name.setText(actor.getName());
            holder.age.setText(String.valueOf(actor.getAge()));
            String imageUrl = actor.getImageUrl();
            if (imageUrl != null && !imageUrl.equals("")) {
                Glide.with(MovieActivity.this)
                        .load(actor.getImageUrl())
                        .into(holder.picture);
            }
        }

        @Override
        public int getItemCount() {
            return MovieController.getMovieActors(movieName).length;
        }
    }
}
