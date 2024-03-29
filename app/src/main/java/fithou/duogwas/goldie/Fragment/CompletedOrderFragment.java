package fithou.duogwas.goldie.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fithou.duogwas.goldie.Adapter.MyOrderAdapter;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Response.InvoiceResponse;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.InvoiceService;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class CompletedOrderFragment extends Fragment {
    ShimmerFrameLayout shimmerMyOrder;
    ConstraintLayout clNoOrder, clOrder;
    RecyclerView rcvOrder;
    Long idStt = Long.valueOf(4);
    List<InvoiceResponse> listInvoiceCompleted = new ArrayList<>();


    public CompletedOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_completed_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getMyOrder(idStt);
    }

    private void initView() {
        clOrder = getView().findViewById(R.id.clOrder);
        clNoOrder = getView().findViewById(R.id.clNoOrder);
        rcvOrder = getView().findViewById(R.id.rcvOrder);
        shimmerMyOrder = getView().findViewById(R.id.shimmerMyOrder);
        shimmerMyOrder.startShimmer();
    }

    private void getMyOrder(Long idStatus) {
        TokenDto user = UserManager.getSavedUser(getContext(), "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        InvoiceService invoiceService = ApiUtils.getInvoiceAPIService();
        Call<List<InvoiceResponse>> call = invoiceService.getListInvoice("Bearer " + token);
        call.enqueue(new Callback<List<InvoiceResponse>>() {
            @Override
            public void onResponse(Call<List<InvoiceResponse>> call, Response<List<InvoiceResponse>> response) {
                if (response.isSuccessful()) {
                    List<InvoiceResponse> invoiceResponses = response.body();
                    if (invoiceResponses.size() == 0) {
                        clOrder.setVisibility(View.GONE);
                        clNoOrder.setVisibility(View.VISIBLE);
                        shimmerMyOrder.hideShimmer();
                        shimmerMyOrder.stopShimmer();
                        shimmerMyOrder.setVisibility(View.GONE);
                    } else {
                        for (InvoiceResponse invoiceResponse : invoiceResponses) {
                            if (invoiceResponse.getStatus().getId().equals(idStatus)) {
                                listInvoiceCompleted.add(invoiceResponse);
                            }
                        }
                        if (listInvoiceCompleted.size() == 0) {
                            clOrder.setVisibility(View.GONE);
                            clNoOrder.setVisibility(View.VISIBLE);
                            shimmerMyOrder.hideShimmer();
                            shimmerMyOrder.stopShimmer();
                            shimmerMyOrder.setVisibility(View.GONE);
                        } else {
                            Collections.sort(listInvoiceCompleted, new Comparator<InvoiceResponse>() {
                                @Override
                                public int compare(InvoiceResponse o1, InvoiceResponse o2) {
                                    return o2.getId().compareTo(o1.getId());
                                }
                            });

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rcvOrder.setLayoutManager(linearLayoutManager);
                            MyOrderAdapter myOrderAdapter = new MyOrderAdapter(listInvoiceCompleted, getContext());
                            rcvOrder.setAdapter(myOrderAdapter);
                            rcvOrder.setVisibility(View.VISIBLE);
                            shimmerMyOrder.hideShimmer();
                            shimmerMyOrder.stopShimmer();
                            shimmerMyOrder.setVisibility(View.GONE);
                        }
                    }
                } else {
                    Log.e("getMyCompletedOrder", "response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<InvoiceResponse>> call, Throwable t) {
                Log.e("getMyCompletedOrder", "onFailure: " + t.getMessage());
            }
        });
    }
}