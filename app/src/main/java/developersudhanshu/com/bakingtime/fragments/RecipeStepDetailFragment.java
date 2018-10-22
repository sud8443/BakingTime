package developersudhanshu.com.bakingtime.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import developersudhanshu.com.bakingtime.utility.Constants;
import developersudhanshu.com.bakingtime.R;
import developersudhanshu.com.bakingtime.lifecycle_components.RecipeStepViewModel;
import developersudhanshu.com.bakingtime.lifecycle_components.RecipeStepViewModelFactory;
import developersudhanshu.com.bakingtime.model.Step;

public class RecipeStepDetailFragment extends Fragment {

    private SimpleExoPlayerView mSimpleExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private TextView descriptionText;
    private String videoUrl;
    private String recipeFullDescription;
    private String thumbnailUrl;
    private Step recipeStep;
    private boolean isVideoPresent;
    private Uri uri;
    private long playerCurrentPosition;
    private boolean isPaused;

    public RecipeStepDetailFragment() {

    }

    @SuppressLint("ValidFragment") // TODO: Check why this error is coming
    public RecipeStepDetailFragment(Step step) {
        this.recipeStep = step;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        mSimpleExoPlayerView = rootView.findViewById(R.id.exo_player_step_video);

        playerCurrentPosition = C.TIME_UNSET;
        isPaused = false;

        if (savedInstanceState != null) {
            playerCurrentPosition = savedInstanceState.getLong(Constants.EXO_PLAYER_POSITION_KEY);
            isPaused = savedInstanceState.getBoolean(Constants.EXO_PLAYER_IS_PAUSED_KEY);
        }

        RecipeStepViewModelFactory factory = new RecipeStepViewModelFactory(recipeStep);
        RecipeStepViewModel viewModel = ViewModelProviders.of(this, factory).get(RecipeStepViewModel.class);
        initializeDataItems(viewModel.getStepDetail());

        initializeViews(rootView);
        if(TextUtils.isEmpty(thumbnailUrl)) {
            mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.video_loading_screen));
        }else{
            Glide.with(getContext())
                    .load(Uri.parse(thumbnailUrl)).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    Bitmap defaultBitmap = ((BitmapDrawable) resource).getBitmap();
                    mSimpleExoPlayerView.setDefaultArtwork(defaultBitmap);
                }
            });

        }

        if(!TextUtils.isEmpty(videoUrl)) {
            isVideoPresent = true;
            uri = Uri.parse(videoUrl);
        }else {
            isVideoPresent = false;
            Toast.makeText(getContext(), "No Video available", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Constants.EXO_PLAYER_POSITION_KEY, playerCurrentPosition);
        outState.putBoolean(Constants.EXO_PLAYER_IS_PAUSED_KEY, isPaused);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVideoPresent)
            initializeExoPlayer(uri);
    }

    private void initializeViews(View rootView) {
        descriptionText = rootView.findViewById(R.id.tv_step_desc_frag_step_detail);

        descriptionText.setText(recipeFullDescription);
    }

    private void initializeDataItems(Step recipeStep) {
        recipeFullDescription = recipeStep.getDescription();
        videoUrl = recipeStep.getVideoURL();
        thumbnailUrl = recipeStep.getThumbnailURL();
    }

    private void initializeExoPlayer(Uri uri) {
        if (mExoPlayer == null){
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            DefaultLoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "BakingTime");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            if (playerCurrentPosition != C.TIME_UNSET)
                mExoPlayer.seekTo(playerCurrentPosition);

            mExoPlayer.prepare(mediaSource);
            if (!isPaused)
                mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isVideoPresent) {
            playerCurrentPosition = mExoPlayer.getCurrentPosition();
            if (mExoPlayer.getPlaybackState() == ExoPlayer.STATE_READY && mExoPlayer.getPlayWhenReady()) {
                isPaused = false;
            }else{
                isPaused = true;
            }
            mExoPlayer.getPlaybackState();
            releasePlayer();
        }
    }
}
