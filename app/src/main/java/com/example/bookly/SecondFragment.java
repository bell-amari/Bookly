package com.example.bookly;

import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bookly.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        ImageView coverItem = binding.bookCover;
        TextView titleItem = binding.title;
        TextView subtitleItem = binding.subtitle;
        TextView authorItem = binding.author;
        TextView pubItem = binding.publisherDate;
        TextView descriptionItem = binding.description;

        String cover, title, subtitle, authors, pub, description;

        Bundle bundle = getArguments();
        if (bundle != null){
            cover = bundle.getString("cover");
            title = bundle.getString("title");
            subtitle = bundle.getString("subtitle");
            authors = bundle.getString("authors");
            pub = bundle.getString("pub");
            description = bundle.getString("description");

            coverItem.setImageURI(Uri.parse(cover));
            titleItem.setText(title);
            subtitleItem.setText(subtitle);
            authorItem.setText(authors);
            pubItem.setText(pub);
            descriptionItem.setText(description);
        }



        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}