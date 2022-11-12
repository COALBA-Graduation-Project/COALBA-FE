package com.example.coalba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coalba.R
import kotlinx.android.synthetic.main.item_month.view.*
import java.util.*

class MonthAdapter:RecyclerView.Adapter<MonthAdapter.MonthView>() {
    val center=Int.MAX_VALUE/2
    private var calendar = Calendar.getInstance()

    inner class MonthView(val layout: View):RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)
        return MonthView(view)
    }

    override fun onBindViewHolder(holder: MonthView, position: Int) {
        // calendar의 time을 현재 날짜로 초기화함
        calendar.time = Date()
        // set을 사용하여 현재 월의 1일로 이동
        calendar.set(Calendar.DAY_OF_MONTH,1)
        // add를 사용하여 월 단위로 'position-center'만큼 이동, center=Int.MAX_VALUE/2이므로 리스트를 좌로 position-center는 -1이 되고 우로 스크롤할 경우 +1이 됨
        // 이렇게 구한 값을 월 단위로 이동함으로써 이전 월과 이후 월을 구할 수 있음
        calendar.add(Calendar.MONTH, position-center)
        // layout에 있는 것을 가져오지 못할 때는 build.gradle->plugins안에 id 'kotlin-android-extensions' 넣고 clean project하기
        holder.layout.tv_month.text = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH)+1}월"
        // 현재 월을 저장
        val tempMonth = calendar.get(Calendar.MONTH)

        // 위에서 보여주고자 하는 월을 구했다면 이제 그 월에서 보여줄 일들을 구하여 Grid 타입의 RecyclerView를 사용하여 각 날짜를 보여줌
        // 6주 * 7일의 날짜를 표시하며 각 정보는 dayList의 저장하여 DayAdapter의 파라미터로 줌
        var dayList:MutableList<Date> = MutableList(6*7){Date()}
        for(i in 0..5){
            for(k in 0..6){
                calendar.add(Calendar.DAY_OF_MONTH,(1-calendar.get(Calendar.DAY_OF_WEEK))+k)
                dayList[i*7+k]=calendar.time
            }
            calendar.add(Calendar.WEEK_OF_MONTH,1)
        }
        val dayListManager = GridLayoutManager(holder.layout.context,7)
        val dayListAdapter = DayAdapter(tempMonth, dayList)
        holder.layout.rv_month.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }
    }

    override fun getItemCount(): Int {
        // 리스트의 항목 개수가 큰 수로 설정
        // 이렇게 한 이유는 리스트를 좌우로 스크롤하였을 경우 이전 월과 이후 월들을 보여주기 위함으로써 scrollToPosition을 사용하여 Int.MAX_VALUE/2서 항목이 시작되도록 설정
        // 그러면 시작 위치인 Int.MAX_VALUE/2를 현재 월로 설정하여 이동할 수 있게 한다면 좌우로 실제로는 끝은 있지만, 거의 수억번 스크롤이 가능함으로 무한 스크롤이 가능한 것처럼 구현 가능
        return Int.MAX_VALUE
    }
}