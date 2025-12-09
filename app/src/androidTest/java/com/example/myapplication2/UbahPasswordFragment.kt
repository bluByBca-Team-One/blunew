
class UbahPasswordFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ubah_password_profile, container, false)
        view.findViewById<Button>(R.id.btnSimpanPassword).setOnClickListener {
            // Logic ubah password
            activity?.supportFragmentManager?.popBackStack()
        }
        return view
    }
}
