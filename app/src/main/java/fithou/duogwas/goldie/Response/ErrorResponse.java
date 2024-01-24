package fithou.duogwas.goldie.Response;
//
// Created by duogwas on 24/01/2024.
//

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ErrorResponse implements Parcelable {

    private int errorCode;
    private String defaultMessage;

    public ErrorResponse() {
    }

    protected ErrorResponse(Parcel in) {
        errorCode = in.readInt();
        defaultMessage = in.readString();
    }

    public static final Creator<ErrorResponse> CREATOR = new Creator<ErrorResponse>() {
        @Override
        public ErrorResponse createFromParcel(Parcel in) {
            return new ErrorResponse(in);
        }

        @Override
        public ErrorResponse[] newArray(int size) {
            return new ErrorResponse[size];
        }
    };

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(errorCode);
        parcel.writeString(defaultMessage);
    }

}

