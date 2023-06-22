package pw.tewi.cawfee.ui.catalog;


import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pw.tewi.cawfee.R;
import pw.tewi.cawfee.models.Post;

/**
 * ArrayAdapter implementation for displaying catalog items in a GridView
 */

@Accessors(fluent=true)
public final class CatalogAdapter extends ArrayAdapter<Post> {
    private static final double CARD_ASPECT_RATIO = 16.0 / 10.0;
    private static final double IMAGE_SIZE_RATIO = 0.5;

    @Getter @Setter private int gridWidth;
    @Getter @Setter private int gridHeight;

    /**
     * Construct a new CatalogAdapter
     *
     * @param context activity context
     */
    @Inject
    public CatalogAdapter(@NonNull @ActivityContext Context context) {
        super(context, 0, new ArrayList<>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View cardView, @NonNull ViewGroup parent) {
        // Inflate a new card view if we don't have one
        if (cardView == null) {
            cardView = LayoutInflater.from(getContext())
                                     .inflate(R.layout.fragment_catalog_card, parent, false);

            cardView.setTag(ViewHolder.builder()
                                      .image(cardView.findViewById(R.id.catalog_card_image))
                                      .subject(cardView.findViewById(R.id.catalog_card_subject))
                                      .comment(cardView.findViewById(R.id.catalog_card_comment))
                                      .build());
        }

        var holder = (ViewHolder) cardView.getTag();
        var post = getItem(position);

        // Image thumbnail set up
        var cvParams = cardView.getLayoutParams();
        var imgParams = holder.image.getLayoutParams();

        cvParams.height = (int) (((GridView) parent).getColumnWidth() * CARD_ASPECT_RATIO);
        imgParams.height = (int) (cvParams.height * IMAGE_SIZE_RATIO);

        cardView.setLayoutParams(cvParams);
        holder.image.setLayoutParams(imgParams);

        holder.image.setClickable(true);
        holder.image.setContentDescription(post.getFilename());
        holder.image.setOnClickListener(__ -> showImageDialog(post.getImageUrl()));

        Glide.with(getContext())
             .load(post.getThumbnailUrl())
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .centerCrop()
             .into(holder.image);

        // Text setup
        holder.subject.setText(Optional.ofNullable(post.getSub()).orElse(""));

        holder.comment.setText(Html.fromHtml(
            Optional.ofNullable(post.getCom()).orElse(""),
            Html.FROM_HTML_MODE_COMPACT
        ));

        return cardView;
    }

    /**
     * Create and display an image dialogue
     *
     * @param url The url of the image to display
     */
    public void showImageDialog(String url) {
        var layout = new FrameLayout(getContext());
        var imageView = new ImageView(getContext());
        var dialog = new AlertDialog.Builder(getContext()).create();

        imageView.setLayoutParams(
            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Glide.with(getContext())
             .load(url)
             .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
             .into(imageView);

        layout.addView(imageView);
        dialog.setView(layout);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    @Builder
    @RequiredArgsConstructor
    private static class ViewHolder {
        final ImageView image;
        final TextView subject;
        final TextView comment;
    }
}
