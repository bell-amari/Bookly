package com.example.bookly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bookly.databinding.FragmentFirstBinding;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiCall();
            }
        });
    }

    public void apiCall(){
        String apiKey = getString(R.string.google_books_api_key);
        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + binding.mainEditText.getText() + "&android&key=" + apiKey;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        JSONArray items = jsonObject.getJSONArray("items");

                        String cover, title, subtitle, authors, pub, description;

                        // Getting the first item on the api response. Will expand in future releases. Amari :)
                        cover = items.getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
                        title = items.getJSONObject(0).getJSONObject("volumeInfo").getString("title");
                        subtitle = items.getJSONObject(0).getJSONObject("volumeInfo").getString("subtitle");
                        authors = items.getJSONObject(0).getJSONObject("volumeInfo").getString("authors");
                        pub = items.getJSONObject(0).getJSONObject("volumeInfo").getString("publisher");
                        description = items.getJSONObject(0).getJSONObject("volumeInfo").getString("description");

                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                binding.textviewFirst.setText(title);

                                Bundle bundle = new Bundle();
                                bundle.putString("cover", cover);
                                bundle.putString("title", title);
                                bundle.putString("subtitle", subtitle);
                                bundle.putString("authors", authors);
                                bundle.putString("pub", pub);
                                bundle.putString("description", description);

                                SecondFragment secondFragment = new SecondFragment();
                                secondFragment.setArguments(bundle);
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                transaction.replace(R.id.SecondFragment, secondFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();

//                                NavHostFragment.findNavController(FirstFragment.this)
//                                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}