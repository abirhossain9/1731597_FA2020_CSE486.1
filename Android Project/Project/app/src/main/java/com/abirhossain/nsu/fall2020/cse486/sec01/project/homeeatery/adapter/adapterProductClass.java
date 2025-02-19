package com.abirhossain.nsu.fall2020.cse486.sec01.project.homeeatery.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abirhossain.nsu.fall2020.cse486.sec01.project.homeeatery.FilterFoods;
import com.abirhossain.nsu.fall2020.cse486.sec01.project.homeeatery.R;
import com.abirhossain.nsu.fall2020.cse486.sec01.project.homeeatery.model.modelFood;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterProductClass extends RecyclerView.Adapter<adapterProductClass.HolderProductSeller> implements Filterable {

    private Context context;
    public ArrayList<modelFood> foodList,filterList;
    private FilterFoods filter;

    public adapterProductClass(Context context, ArrayList<modelFood> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.filterList = foodList;
    }

    @NonNull
    @Override
    public HolderProductSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate Layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_food_seller,parent,false);
        return new HolderProductSeller(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductSeller holder, int position) {
        //getting data
        modelFood ModelFood = foodList.get(position);
        String id = ModelFood.getFoodId();
        String uid = ModelFood.getUid();
        String discountAvailable = ModelFood.getDiscountAvailable();
        String discountNote = ModelFood.getDiscountNote();
        String discountPrice = ModelFood.getDiscountPrice();
        String foodCategory = ModelFood.getFoodCategory();
        String foodDescription = ModelFood.getFoodDescription();
        String foodIcon = ModelFood.getFoodIcon();
        String foodQuantity = ModelFood.getFoodQuantity();
        String title = ModelFood.getFoodTitle();
        String timeStamp = ModelFood.getTimestamp();
        String OriginalPrice = ModelFood.getOriginalPrice();

        //setting data
        holder.titleTV.setText(title);
        holder.FoodQuantityTV.setText(foodQuantity);
        holder.priceDiscounted.setText("$"+discountPrice);
        holder.discountNote.setText(discountNote);
        holder.originalPrice.setText("$"+OriginalPrice);
        if(discountAvailable.equals("true")){
            //product with discount
            holder.priceDiscounted.setVisibility(View.VISIBLE);
            holder.discountNote.setVisibility(View.VISIBLE);

        }
        else {
            //product without discount
            holder.priceDiscounted.setVisibility(View.GONE);
            holder.discountNote.setVisibility(View.GONE);
        }
        try {
            Picasso.get().load(foodIcon).placeholder(R.drawable.food_img).into(holder.foodIconIV);

        }
        catch (Exception e){
            holder.foodIconIV.setImageResource(R.drawable.food_img);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // food details
                foodDetails(ModelFood); // model food contains all the details of the food
            }
        });



    }

    private void foodDetails(modelFood ModelFood) {
        //bottom sheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        //inflating view
        View view = LayoutInflater.from(context).inflate(R.layout.product_details_vendor,null);
        //setting content
        bottomSheetDialog.setContentView(view);

        //initializing views
        ImageView backBtnOrderDetails,Seller_delete_btn,Seller_edit_btn,foodIconIVOrderDetails;
        TextView OrderDetailsTVHeader,discountNoteTVOrderDetails,FoodTitleTVOrderDetails,FoodDescriptionTVOrderDetails,FoodCategoryTVOrderDetails,
                FoodQuantityTVOrderDetails,priceDiscountedOrderDetails,originalPriceOrderDetails;
        //ui initializing
        backBtnOrderDetails = view.findViewById(R.id.backBtnOrderDetails);
        Seller_delete_btn = view.findViewById(R.id.Seller_delete_btn);
        Seller_edit_btn = view.findViewById(R.id.Seller_edit_btn);
        foodIconIVOrderDetails = view.findViewById(R.id.foodIconIVOrderDetails);
        OrderDetailsTVHeader = view.findViewById(R.id.OrderDetailsTV);
        discountNoteTVOrderDetails = view.findViewById(R.id.discountNoteTVOrderDetails);
        FoodTitleTVOrderDetails = view.findViewById(R.id.FoodTitleTVOrderDetails);
        FoodDescriptionTVOrderDetails = view.findViewById(R.id.FoodDescriptionTVOrderDetails);
        FoodCategoryTVOrderDetails = view.findViewById(R.id.FoodCategoryTVOrderDetails);
        FoodQuantityTVOrderDetails = view.findViewById(R.id.FoodQuantityTVOrderDetails);
        priceDiscountedOrderDetails = view.findViewById(R.id.priceDiscountedOrderDetails);
        originalPriceOrderDetails = view.findViewById(R.id.originalPriceOrderDetails);

        //getting data

        String id = ModelFood.getFoodId();
        String uid = ModelFood.getUid();
        String discountAvailable = ModelFood.getDiscountAvailable();
        String discountNote = ModelFood.getDiscountNote();
        String discountPrice = ModelFood.getDiscountPrice();
        String foodCategory = ModelFood.getFoodCategory();
        String foodDescription = ModelFood.getFoodDescription();
        String foodIcon = ModelFood.getFoodIcon();
        String foodQuantity = ModelFood.getFoodQuantity();
        String title = ModelFood.getFoodTitle();
        String timeStamp = ModelFood.getTimestamp();
        String OriginalPrice = ModelFood.getOriginalPrice();

        //setting data
        FoodTitleTVOrderDetails.setText(title);
        FoodDescriptionTVOrderDetails.setText(foodDescription);
        FoodCategoryTVOrderDetails.setText(foodCategory);
        FoodQuantityTVOrderDetails.setText(foodQuantity);
        discountNoteTVOrderDetails.setText(discountNote);
        priceDiscountedOrderDetails.setText("$"+discountPrice);
        originalPriceOrderDetails.setText("$"+OriginalPrice);
        //showing dialog
        bottomSheetDialog.show();

        if(discountAvailable.equals("true")){
            //product with discount
            priceDiscountedOrderDetails.setVisibility(View.VISIBLE);
            discountNoteTVOrderDetails.setVisibility(View.VISIBLE);

        }
        else {
            //product without discount
            priceDiscountedOrderDetails.setVisibility(View.GONE);
            discountNoteTVOrderDetails.setVisibility(View.GONE);
        }
        try {
            Picasso.get().load(foodIcon).placeholder(R.drawable.food_img).into(foodIconIVOrderDetails);

        }
        catch (Exception e){
            foodIconIVOrderDetails.setImageResource(R.drawable.food_img);

        }

        // handling buttons
        //edit button
        Seller_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //edit product activity
                Toast.makeText(context, "Work in progress", Toast.LENGTH_SHORT).show();

            }
        });
        //delete button
        Seller_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete product
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Food")
                        .setMessage("Are you sure you want to delete "+title+"?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete food
                                deleteFood(id);

                            }
                        }).setNegativeButton("KEEP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //deletion cancel
                        dialog.dismiss();

                    }
                }).show();


            }
        });
        //back button
        backBtnOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

            }
        });


    }

    private void deleteFood(String id) {
       //delete food using its id
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("Foods").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //successfully deleted the food
                        Toast.makeText(context, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //couldn't delete
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    @Override
    public  Filter getFilter() {
        if (filter == null){
            filter= new FilterFoods(this,filterList);
        }

        return filter;
    }

    class HolderProductSeller extends RecyclerView.ViewHolder{
        /*holds views of recyclerView*/
        private ImageView foodIconIV;
        private TextView discountNote,titleTV,priceDiscounted,originalPrice,FoodQuantityTV;


        public HolderProductSeller(@NonNull View itemView) {
            super(itemView);
            foodIconIV = itemView.findViewById(R.id.foodIcon);
            discountNote = itemView.findViewById(R.id.discountNoteTV);
            titleTV = itemView.findViewById(R.id.FoodTitleTV);
            priceDiscounted = itemView.findViewById(R.id.priceDiscounted);
            originalPrice = itemView.findViewById(R.id.originalPrice);
            FoodQuantityTV = itemView.findViewById(R.id.FoodQuantityTV);




        }
    }
}
