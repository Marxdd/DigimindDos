package lopez.mario.digiminddos.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import lopez.mario.digiminddos.AdaptadorTareas
import lopez.mario.digiminddos.databinding.FragmentHomeBinding
import lopez.mario.digiminddos.ui.Task

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    companion object{
        var tasks: ArrayList<Task> = ArrayList<Task>()
        var first = true
        lateinit var adaptador: AdaptadorTareas
    }

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val gridView: GridView = binding.gridview
        //     if (first){
        //       fillTasks()
        //     first = false
        //}
        cargarTareas()
        adaptador = AdaptadorTareas(root.context,tasks)

        gridView.adapter = adaptador

        return root
    }

    fun fillTasks(){
        tasks.add(Task("Tarea1","Lunes","15:00"))
        tasks.add(Task("Tarea2","Lunes","15:00"))
        tasks.add(Task("Tarea3","Lunes","15:00"))
        tasks.add(Task("Tarea4","Lunes","15:00"))
    }

    fun cargarTareas(){
        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson: Gson = Gson()

        var json = preferencias?.getString("tareas",null)

        val type = object : TypeToken<ArrayList<Task>?>(){}.type

        if (json==null){
            tasks = ArrayList<Task>()
        } else{
            tasks = gson.fromJson(json,type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}