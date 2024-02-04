package com.example.nesthub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.nesthub.adapters.CatAdapter;
import com.example.nesthub.adapters.ExploreAdapter;
import com.example.nesthub.databinding.FragmentExploreBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.example.nesthub.R;


public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;
    private ExploreAdapter exploreAdapter;
    private ShimmerFrameLayout shimmerViewContainer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        exploreAdapter = new ExploreAdapter(requireContext(), binding.exploreitems);
        shimmerViewContainer = root.findViewById(R.id.shimmerLayout);

        // Start shimmer effect
        shimmerViewContainer.startShimmer();

        // Fetch data and stop shimmer when done
        exploreAdapter.fetchNestsData(new ExploreAdapter.OnDataFetchCompleteListener() {
            @Override
            public void onDataFetchComplete() {
                // Stop shimmer effect
                shimmerViewContainer.stopShimmer();
                shimmerViewContainer.setVisibility(View.GONE);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
