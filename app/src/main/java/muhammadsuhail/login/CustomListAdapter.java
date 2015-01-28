package muhammadsuhail.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muhammad Suhail on 03/01/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    static class ViewHolder{

        TextView company_name;
        TextView colour;
        TextView city;
    }
    public ArrayList<Wines> wines;
    public LayoutInflater layoutInflater;
    public CustomListAdapter(Context context, ArrayList<Wines> wines)
    {

 this.wines=wines;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return wines.size();
    }

    @Override
    public Object getItem(int position) {
        return wines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if(convertView==null)
        {
            convertView=layoutInflater.inflate(R.layout.activity2,null);
            holder=new ViewHolder();
            holder.company_name=(TextView)convertView.findViewById(R.id.company_name);
            holder.colour=(TextView)convertView.findViewById(R.id.colour);
            holder.city=(TextView)convertView.findViewById(R.id.city);

            convertView.setTag(holder);
        }
else{
holder=(ViewHolder)convertView.getTag();
        }

Wines obj=(Wines)wines.get(position);
holder.company_name.setText(obj.get_company_name());
        holder.city.setText(obj.get_city());
        holder.colour.setText(obj.get_colour());

return convertView;
    }
}
