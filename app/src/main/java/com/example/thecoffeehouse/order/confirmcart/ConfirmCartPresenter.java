package com.example.thecoffeehouse.order.confirmcart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.thecoffeehouse.data.model.bill.Bill;
import com.example.thecoffeehouse.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import androidx.annotation.NonNull;

public class ConfirmCartPresenter implements IConfirmCartContract.Presenter {

    private IConfirmCartContract.View callback;
    private DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Bill");
    public ConfirmCartPresenter(IConfirmCartContract.View callback){
        this.callback = callback;
    }

    @Override
    public void confirmCart(String numberPhone, int totalPrice, int totalCup, int timeCount) {
        Bill mBill = new Bill(totalPrice,totalCup,timeCount);
        mDataRef.orderByKey().equalTo(numberPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                        Bill bill1 = dataSnapshot1.getValue(Bill.class);
                        int totalPriceNew = bill1.getTotalPrice()+totalPrice;
                        int totalCupNew = bill1.getCupCount()+totalCup;
                        int timeCountNew = bill1.getTimeCount()+timeCount;
                        Bill bill2 = new Bill(totalPriceNew,totalCupNew,timeCountNew);
                        mDataRef.child(numberPhone).setValue(bill2).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                callback.onConfirmSucess("Success");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callback.onConfirmFaild("Fail");
                            }
                        });

                    }
                }
                else {
                    mDataRef.child(numberPhone).setValue(mBill).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            callback.onConfirmSucess("Success");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onConfirmFaild("Fail");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
