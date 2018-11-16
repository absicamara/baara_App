package net.geeksh.baaraapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomViewAdapter extends ArrayAdapter<Offer> {



        public CustomViewAdapter(Context context, int resource, List<Offer> foods) {
            super(context, resource, foods);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // default -  return super.getView(position, convertView, parent);
            // add the layout
            LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
            View customView = myCustomInflater.inflate(R.layout.custom_listview, parent, false);
            // get references.
            Offer singleFoodItem = getItem(position);
            TextView itemText = (TextView) customView.findViewById(R.id.txtViewJobTitle);
            TextView itemText2 = (TextView) customView.findViewById(R.id.txtViewJobDescription);
            ImageView buckysImage = (ImageView) customView.findViewById(R.id.ImgViewOfferLogo);



            // dynamically update the text from the array
            itemText.setText(singleFoodItem.jobTitle);
            itemText2.setText(singleFoodItem.description);
            // using the same image every time
            buckysImage.setImageResource(R.drawable.ic_business_center_black_24dp);
            // Now we can finally return our custom View or custom item
            return customView;
        }

}
