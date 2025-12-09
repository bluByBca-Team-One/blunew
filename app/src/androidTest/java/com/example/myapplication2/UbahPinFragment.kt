class UbahPinFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ubah_pin_profile, container, false)
        view.findViewById<Button>(R.id.btnSimpanPin).setOnClickListener {
            // Logic ubah pin
            activity?.supportFragmentManager?.popBackStack()
        }
        return view
    }
}