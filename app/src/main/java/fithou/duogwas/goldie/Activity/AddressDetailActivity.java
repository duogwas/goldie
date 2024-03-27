package fithou.duogwas.goldie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fithou.duogwas.goldie.Adapter.DistrictsAdapter;
import fithou.duogwas.goldie.Adapter.ProvinceAdapter;
import fithou.duogwas.goldie.Adapter.WardsAdapter;
import fithou.duogwas.goldie.Entity.Districts;
import fithou.duogwas.goldie.Entity.Province;
import fithou.duogwas.goldie.Entity.Wards;
import fithou.duogwas.goldie.R;
import fithou.duogwas.goldie.Request.UserAdressRequest;
import fithou.duogwas.goldie.Response.TokenDto;
import fithou.duogwas.goldie.Response.UserAdressResponse;
import fithou.duogwas.goldie.Retrofit.AddressService;
import fithou.duogwas.goldie.Retrofit.ApiUtils;
import fithou.duogwas.goldie.Retrofit.UserAddressService;
import fithou.duogwas.goldie.Utils.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.thanguit.toastperfect.ToastPerfect;

public class AddressDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivBack;
    TextView tvTittle, tvErrName, tvErrPhone, tvErrStreetName;
    EditText edtFullName, edtStreetName, edtPhone;
    AppCompatSpinner spnProvince, spnDistrict, spnWard;
    AppCompatCheckBox cbPrimary;
    AppCompatButton btnUpdate, btnDelete, btnCreate;
    List<Province> provinceList = new ArrayList<>();
    List<Districts> districtsList = new ArrayList<>();
    List<Wards> wardsList = new ArrayList<>();
    Long idProvince, idDistrict, idWards;
    Long idAddress, idProvinceI, idDistrictI, idWardsI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_address_detail);
        initView();
        setOnClick();
        getDataIntent();
        updateUI(idAddress);
        loadProvince();
        getMyAddressDetail(idAddress);
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        tvTittle = findViewById(R.id.tvTittle);
        tvErrName = findViewById(R.id.tvErrName);
        tvErrPhone = findViewById(R.id.tvErrPhone);
        tvErrStreetName = findViewById(R.id.tvErrStreetName);
        edtFullName = findViewById(R.id.edtFullName);
        edtStreetName = findViewById(R.id.edtStreetName);
        edtPhone = findViewById(R.id.edtPhone);
        spnProvince = findViewById(R.id.spnProvince);
        spnDistrict = findViewById(R.id.spnDistrict);
        spnWard = findViewById(R.id.spnWard);
        cbPrimary = findViewById(R.id.cbPrimary);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCreate = findViewById(R.id.btnCreate);
    }

    private void setOnClick() {
        ivBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        idAddress = intent.getLongExtra("idAddress", -1);
        idProvinceI = intent.getLongExtra("idProvince", -1);
        idDistrictI = intent.getLongExtra("idDistrict", -1);
        idWardsI = intent.getLongExtra("idWards", -1);
    }

    private void updateUI(Long idAddress) {
        if (idAddress == -1) {
            tvTittle.setText("ĐỊA CHỈ MỚI");
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnCreate.setVisibility(View.VISIBLE);
        }

    }

    private int getPositionOfProvince(long idProvince) {
        for (int i = 0; i < provinceList.size(); i++) {
            Province province = provinceList.get(i);
            if (province.getId().equals(idProvince)) {
                return i;
            }
        }
        return -1;
    }

    private int getPositionOfDistrict(long idDistrict) {
        for (int i = 0; i < districtsList.size(); i++) {
            Districts district = districtsList.get(i);
            if (district.getId().equals(idDistrict)) {
                return i;
            }
        }
        return -1;
    }

    private int getPositionOfWards(long idWards) {
        for (int i = 0; i < districtsList.size(); i++) {
            Wards wards = wardsList.get(i);
            if (wards.getId().equals(idWards)) {
                return i;
            }
        }
        return -1;
    }

    private void loadProvince() {
        AddressService addressService = ApiUtils.getAddressAPIService();
        Call<List<Province>> call = addressService.getProvinceList();
        call.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if (response.isSuccessful()) {
                    provinceList = response.body();
                    ProvinceAdapter provinceAdapter = new ProvinceAdapter(AddressDetailActivity.this, R.layout.item_address, provinceList);
                    provinceAdapter.setDropDownViewResource(R.layout.item_address);
                    spnProvince.setAdapter(provinceAdapter);

                    if (idProvinceI != -1) {
                        int position = getPositionOfProvince(idProvinceI);
                        spnProvince.setSelection(position);
                    }

                    spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            idProvinceI = -1L;
                            Province selectedProvince = (Province) adapterView.getItemAtPosition(position);
                            idProvince = selectedProvince.getId();
                            loadDistricts(idProvince);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {

            }
        });
    }

    private void loadDistricts(Long idProvince) {
        for (Province province : provinceList) {
            if (province.getId() == idProvince) {
                districtsList = province.getDistricts();
                DistrictsAdapter districtsAdapter = new DistrictsAdapter(AddressDetailActivity.this, R.layout.item_address, districtsList);
                districtsAdapter.setDropDownViewResource(R.layout.item_address);
                spnDistrict.setAdapter(districtsAdapter);

                if (idDistrictI != -1) {
                    int position = getPositionOfDistrict(idDistrictI);
                    spnDistrict.setSelection(position);
                }

                spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        idDistrictI = -1L;
                        Districts selectedDistrict = (Districts) adapterView.getItemAtPosition(position);
                        idDistrict = selectedDistrict.getId();
                        loadWards(idDistrict);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                break;
            }
        }
    }

    private void loadWards(Long idDistrict) {
        for (Districts districts : districtsList) {
            if (districts.getId() == idDistrict) {
                wardsList = districts.getWards();
                WardsAdapter wardsAdapter = new WardsAdapter(AddressDetailActivity.this, R.layout.item_address, wardsList);
                wardsAdapter.setDropDownViewResource(R.layout.item_address);
                spnWard.setAdapter(wardsAdapter);

                if (idWardsI != -1) {
                    int position1 = getPositionOfWards(idWardsI);
                    spnWard.setSelection(position1);
                }

                spnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        idWardsI = -1L;
                        Wards selectedWard = (Wards) adapterView.getItemAtPosition(position);
                        idWards = selectedWard.getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                break;
            }
        }
    }

    private void getMyAddressDetail(Long id) {
        TokenDto user = UserManager.getSavedUser(AddressDetailActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        UserAddressService userAddressService = ApiUtils.getUserAddressAPIService();
        Call<UserAdressResponse> call = userAddressService.getAddressDetail("Bearer " + token, id);
        call.enqueue(new Callback<UserAdressResponse>() {
            @Override
            public void onResponse(Call<UserAdressResponse> call, Response<UserAdressResponse> response) {
                if (response.isSuccessful()) {
                    UserAdressResponse userAdressResponse = response.body();
                    edtFullName.setText(userAdressResponse.getFullname());
                    edtPhone.setText(userAdressResponse.getPhone());
                    edtStreetName.setText(userAdressResponse.getStreetName());
                    if (userAdressResponse.getPrimaryAddres()) {
                        cbPrimary.setChecked(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserAdressResponse> call, Throwable t) {

            }
        });
    }

    private void createMyAddress() {
        UserAdressRequest userAdressRequest;
        Long id = null;
        String fullName = edtFullName.getText().toString();
        String phone = edtPhone.getText().toString();
        String streetName = edtStreetName.getText().toString();
        Boolean primaryAddres = cbPrimary.isChecked();
        Wards wards = new Wards(idWards);

        if (fullName.isEmpty()) {
            tvErrName.setVisibility(View.VISIBLE);
            tvErrName.setText("Vui lòng nhập Họ và tên");
        }

        if (phone.isEmpty()) {
            tvErrPhone.setVisibility(View.VISIBLE);
            tvErrPhone.setText("Vui lòng nhập Số điện thoại");
        }

        if (streetName.isEmpty()) {
            tvErrStreetName.setVisibility(View.VISIBLE);
            tvErrStreetName.setText("Vui lòng nhập Địa chỉ");
        }

        if (edtFullName.getText().toString().trim().isEmpty() ||
                edtPhone.getText().toString().trim().isEmpty() ||
                edtStreetName.getText().toString().trim().isEmpty()) {
            return;
        } else {
            userAdressRequest = new UserAdressRequest(id, fullName, phone, streetName, primaryAddres, wards);
        }
        TokenDto user = UserManager.getSavedUser(AddressDetailActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        UserAddressService userAddressService = ApiUtils.getUserAddressAPIService();
        Call<UserAdressResponse> call = userAddressService.creatAddress("Bearer " + token, userAdressRequest);
        call.enqueue(new Callback<UserAdressResponse>() {
            @Override
            public void onResponse(Call<UserAdressResponse> call, Response<UserAdressResponse> response) {
                if (response.isSuccessful()) {
                    UserAdressResponse result = response.body();
                    startActivity(new Intent(AddressDetailActivity.this, MyAddressActivity.class));
                    ToastPerfect.makeText(AddressDetailActivity.this, ToastPerfect.SUCCESS, "Tạo địa chỉ thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                } else {
                    ToastPerfect.makeText(AddressDetailActivity.this, ToastPerfect.ERROR, "Tạo địa chỉ không thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserAdressResponse> call, Throwable t) {
                ToastPerfect.makeText(AddressDetailActivity.this, ToastPerfect.ERROR, "Tạo địa chỉ ònail", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();

            }
        });

    }

    private void updateMyAddress(Long id) {
        UserAdressRequest userAdressRequest;
        String fullName = edtFullName.getText().toString();
        String phone = edtPhone.getText().toString();
        String streetName = edtStreetName.getText().toString();
        Boolean primaryAddres = cbPrimary.isChecked();
        Wards wards = new Wards(idWards);

        if (fullName.isEmpty()) {
            tvErrName.setVisibility(View.VISIBLE);
            tvErrName.setText("Vui lòng nhập Họ và tên");
        }

        if (phone.isEmpty()) {
            tvErrPhone.setVisibility(View.VISIBLE);
            tvErrPhone.setText("Vui lòng nhập Số điện thoại");
        }

        if (streetName.isEmpty()) {
            tvErrStreetName.setVisibility(View.VISIBLE);
            tvErrStreetName.setText("Vui lòng nhập Địa chỉ");
        }

        if (edtFullName.getText().toString().trim().isEmpty() ||
                edtPhone.getText().toString().trim().isEmpty() ||
                edtStreetName.getText().toString().trim().isEmpty()) {
            return;
        } else {
            userAdressRequest = new UserAdressRequest(id, fullName, phone, streetName, primaryAddres, wards);
        }
        TokenDto user = UserManager.getSavedUser(AddressDetailActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        UserAddressService userAddressService = ApiUtils.getUserAddressAPIService();
        Call<UserAdressResponse> call = userAddressService.updateAddress("Bearer " + token, userAdressRequest);
        call.enqueue(new Callback<UserAdressResponse>() {
            @Override
            public void onResponse(Call<UserAdressResponse> call, Response<UserAdressResponse> response) {
                if (response.isSuccessful()) {
                    UserAdressResponse result = response.body();
                    startActivity(new Intent(AddressDetailActivity.this, MyAddressActivity.class));
                    ToastPerfect.makeText(AddressDetailActivity.this, ToastPerfect.SUCCESS, "Cập nhật địa chỉ thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                } else {
                    ToastPerfect.makeText(AddressDetailActivity.this, ToastPerfect.ERROR, "Cập nhật địa chỉ không thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserAdressResponse> call, Throwable t) {
                ToastPerfect.makeText(AddressDetailActivity.this, ToastPerfect.ERROR, "Tạo địa chỉ ònail", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();

            }
        });
    }

    private void deleteMyAddress(Long id) {
        TokenDto user = UserManager.getSavedUser(AddressDetailActivity.this, "User", "MODE_PRIVATE", TokenDto.class);
        String token = user.getToken();
        UserAddressService userAddressService = ApiUtils.getUserAddressAPIService();
        Call<Void> call = userAddressService.deleteAddress("Bearer " + token, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(AddressDetailActivity.this, MyAddressActivity.class));
                    ToastPerfect.makeText(AddressDetailActivity.this, ToastPerfect.SUCCESS, "Xóa địa chỉ thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                } else {
                    ToastPerfect.makeText(AddressDetailActivity.this, ToastPerfect.INFORMATION, "Xóa địa chỉ ko thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    private void showDialogDelete() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_address);
        AppCompatButton btnDelete = dialog.findViewById(R.id.btnDelete);
        AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancel);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMyAddress(idAddress);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottomDialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;

            case R.id.btnUpdate:
                updateMyAddress(idAddress);
                break;

            case R.id.btnDelete:
                showDialogDelete();
                break;

            case R.id.btnCreate:
                createMyAddress();
                break;

            default:
                break;
        }

    }
}