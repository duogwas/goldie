package fithou.duogwas.goldie.Response;
//
// Created by duogwas on 29/01/2024.
//


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page<T> {

    @SerializedName("content")
    private List<T> content;

    @SerializedName("totalElements")
    private int totalElements;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }


}

