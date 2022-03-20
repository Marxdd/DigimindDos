package lopez.mario.digiminddos.ui.dashboard

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import lopez.mario.digiminddos.R
import lopez.mario.digiminddos.databinding.FragmentDashboardBinding
import lopez.mario.digiminddos.ui.Task
import lopez.mario.digiminddos.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnTime.setOnClickListener{
            setTime()
        }

        binding.btnSave.setOnClickListener{
            if (binding.etTask.text !=null && binding.btnTime.text  !="HORA" && binding.rgDias.checkedRadioButtonId!=-1){
                guardar()
            } else{
                Toast.makeText(context,"Llenar campos necesarios", Toast.LENGTH_LONG).show()
            }

        }

        return root
    }
    fun guardar(){
        var titulo: String = binding.etTask.text.toString()
        var tiempo: String = binding.btnTime.text.toString()
        var dia: String = ""

        if (binding.rbDay1.isChecked) dia = getString(R.string.day1)
        if (binding.rbDay2.isChecked) dia = getString(R.string.day2)
        if (binding.rbDay3.isChecked) dia = getString(R.string.day3)
        if (binding.rbDay4.isChecked) dia = getString(R.string.day4)
        if (binding.rbDay5.isChecked) dia = getString(R.string.day5)
        if (binding.rbDay6.isChecked) dia = getString(R.string.day6)
        if (binding.rbDay7.isChecked) dia = getString(R.string.day7)

        var tarea = Task(titulo,dia,tiempo)

        HomeFragment.tasks.add(tarea)

        Toast.makeText(context,"Se agrego la tarea", Toast.LENGTH_SHORT).show()
        guardarJson()
    }

    fun guardarJson(){
        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = preferencias?.edit()

        val gson: Gson = Gson()
        var json = gson.toJson(HomeFragment.tasks)

        editor?.putString("tareas",json)
        editor?.apply()


    }

    fun setTime(){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)

            binding.btnTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),true).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}