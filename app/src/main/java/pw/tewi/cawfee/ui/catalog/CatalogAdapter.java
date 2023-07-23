package pw.tewi.cawfee.ui.catalog;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import pw.tewi.cawfee.databinding.FragmentCatalogCardBinding;
import pw.tewi.cawfee.models.Post;
import pw.tewi.cawfee.ui.imagedialog.ImageDialogFragment;

/**
 * ArrayAdapter implementation for displaying catalog items in a GridView
 */

@Accessors(fluent = true)
public final class CatalogAdapter extends ArrayAdapter<Post> {
    private static final double CARD_ASPECT_RATIO = 16.0 / 10.0;
    private static final double IMAGE_SIZE_RATIO = 0.4;
    private static final String POST_INFO_FORMAT = "%sR %sI";

    @Getter @Setter private int gridWidth;
    @Getter @Setter private int gridHeight;

    /**
     * Construct a new CatalogAdapter
     *
     * @param context activity context
     */
    @Inject
    public CatalogAdapter(@NonNull @ActivityContext Context context) {
        super((Context) context, 0, new ArrayList<>());
    }

    private FragmentManager getSupportFragmentManager() {
        return ((AppCompatActivity) getContext()).getSupportFragmentManager();
    }

    private String getThreadInfo(Post post) {
        return String.format(POST_INFO_FORMAT, post.getReplies(), post.getImages());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View cardView, @NonNull ViewGroup parent) {

        @SuppressLint("ViewHolder")
        var b = Optional.ofNullable(cardView)
            .map(FragmentCatalogCardBinding::bind)
            .orElse(FragmentCatalogCardBinding.inflate(LayoutInflater.from(getContext())));

        var thread = getItem(position);
        // Image thumbnail set up
        var cardViewLayoutParams = b.cardLayout.getLayoutParams();
        var imageLayoutParams = b.image.getLayoutParams();

        cardViewLayoutParams.height = (int) (((GridView) parent).getColumnWidth() * CARD_ASPECT_RATIO);
        imageLayoutParams.height = (int) (cardViewLayoutParams.height * IMAGE_SIZE_RATIO);

        b.cardLayout.setLayoutParams(cardViewLayoutParams);
        b.image.setLayoutParams(imageLayoutParams);

        b.image.setContentDescription(thread.getFilename());
        b.image.setOnClickListener(__ -> ImageDialogFragment.from(thread).show(getSupportFragmentManager(), "image" ));

        Glide.with(getContext())
             .load(thread.getThumbnailUrl())
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .centerCrop()
             .into(b.image);

        // Text setup
        b.info.setText(getThreadInfo(thread));

        if (thread.getSub() != null) {
            b.subject.setText(Html.fromHtml(thread.getSub(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            b.subject.setHeight(0);
        }

        if (thread.getCom() != null) {
            b.comment.setText(Html.fromHtml(thread.getCom(), Html.FROM_HTML_MODE_COMPACT));
        }

        return b.getRoot();
    }
}