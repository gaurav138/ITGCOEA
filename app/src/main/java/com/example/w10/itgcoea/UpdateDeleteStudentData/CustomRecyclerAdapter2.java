package com.example.w10.itgcoea.UpdateDeleteStudentData;

/**
 * Created by W10 on 12/02/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.w10.itgcoea.R;
import com.example.w10.itgcoea.UpdateFaculty.UpdateDeleteFac;

import java.util.List;


public class CustomRecyclerAdapter2 extends RecyclerView.Adapter<CustomRecyclerAdapter2.ViewHolder> {

    private Context context;
    private String year,data;
    private List<StudentList> studentList;


    public CustomRecyclerAdapter2(Context context, List studentList, String year, String data) {
        this.context = context;
        this.studentList = studentList;
        this.year=year;
        this.data=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item2, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(studentList.get(position));

        StudentList pu = studentList.get(position);

        holder.sName.setText(pu.getName());
        if(data.equals("stud"))
        holder.sRollNo.setText(pu.getRoll_no()+"");
        else
            holder.sRollNo.setText(pu.getCourses());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView sName;
        public TextView sRollNo;

        public ViewHolder(View itemView) {
            super(itemView);

            sName = (TextView) itemView.findViewById(R.id.sNametxt);
            sRollNo = (TextView) itemView.findViewById(R.id.sRollNo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    StudentList cpu = (StudentList) view.getTag();

                    //Toast.makeText(view.getContext(), cpu.getName()+" "+cpu.getUsername()+" is "+ cpu.getEmail(), Toast.LENGTH_SHORT).show();
                    if(data.equals("stud"))
                    {
                        Intent intent=new Intent(context,UpdateDelete.class);
                        intent.putExtra("username",cpu.getUsername());
                        intent.putExtra("name",cpu.getName());
                        intent.putExtra("password",cpu.getPassword());
                        intent.putExtra("roll_no",cpu.getRoll_no());
                        intent.putExtra("user_id",cpu.getUser_id());
                        intent.putExtra("email",cpu.getEmail());
                        intent.putExtra("year",year);
                        context.startActivity(intent);
                    }
                    else
                    {
                        Intent intent=new Intent(context,UpdateDeleteFac.class);
                        intent.putExtra("username",cpu.getUsername());
                        intent.putExtra("name",cpu.getName());
                        intent.putExtra("password",cpu.getPassword());
                        intent.putExtra("courses",cpu.getCourses());
                        intent.putExtra("user_id",cpu.getUser_id());
                        intent.putExtra("email",cpu.getEmail());
                        intent.putExtra("qualification",cpu.getQualification());
                        context.startActivity(intent);
                    }

                }
            });

        }
    }

}