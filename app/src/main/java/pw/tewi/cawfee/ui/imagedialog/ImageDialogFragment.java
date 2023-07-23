package pw.tewi.cawfee.ui.imagedialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import pw.tewi.cawfee.R;
import pw.tewi.cawfee.databinding.FragmentImageDialogBinding;
import pw.tewi.cawfee.models.Post;

@Accessors(fluent = true)
@RequiredArgsConstructor(access=AccessLevel.PRIVATE)
public class ImageDialogFragment extends DialogFragment {

    @NonNull private final String imageUrl;

    public static ImageDialogFragment from(@NonNull Post post) {
        return new ImageDialogFragment(post.getImageUrl());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var dialog = new AlertDialog.Builder(requireContext(), android.R.style.ThemeOverlay).create();
        var binding = FragmentImageDialogBinding.inflate(getLayoutInflater());

        Glide.with(requireContext())
            .load(imageUrl)
            .centerInside()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.image);

        dialog.setView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent_black);

        return dialog;
    }
}
