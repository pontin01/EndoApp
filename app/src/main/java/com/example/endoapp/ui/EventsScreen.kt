package com.example.endoapp.ui

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.endoapp.R
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Event(
    val title: String,
    val date: LocalDate,
    val time: String,
    val location: String,
    val description: String
)

// ---------- Calendar grid ----------
class CalendarAdapter(
    private var days: List<LocalDate?>,
    private var eventDates: Set<LocalDate>,
    private var selected: LocalDate?,
    private val onDayClick: (LocalDate) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val cell: View = v.findViewById(R.id.dayCell)
        val number: TextView = v.findViewById(R.id.dayNumber)
        val dot: View = v.findViewById(R.id.eventDot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day, parent, false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(h: VH, position: Int) {
        val date = days[position]
        if (date == null) {
            h.number.text = ""
            h.number.setBackgroundResource(0)
            h.dot.visibility = View.INVISIBLE
            h.cell.setOnClickListener(null)
            h.cell.isClickable = false
            return
        }

        h.number.text = date.dayOfMonth.toString()
        h.dot.visibility = if (date in eventDates) View.VISIBLE else View.INVISIBLE
        h.cell.isClickable = true

        when {
            date == selected -> {
                h.number.setBackgroundResource(R.drawable.day_selected_bg)
                h.number.setTextColor(Color.WHITE)
            }
            date == LocalDate.now() -> {
                h.number.setBackgroundResource(0)
                h.number.setTextColor(Color.parseColor("#C1577B"))
            }
            else -> {
                h.number.setBackgroundResource(0)
                h.number.setTextColor(Color.parseColor("#2B1F27"))
            }
        }

        h.cell.setOnClickListener { onDayClick(date) }
    }

    override fun getItemCount() = days.size

    fun update(days: List<LocalDate?>, selected: LocalDate?) {
        this.days = days
        this.selected = selected
        notifyDataSetChanged()
    }
}

// ---------- Event list ----------
class EventAdapter(private var items: List<Event>) : RecyclerView.Adapter<EventAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.eventTitle)
        val time: TextView = v.findViewById(R.id.eventTime)
        val location: TextView = v.findViewById(R.id.eventLocation)
        val description: TextView = v.findViewById(R.id.eventDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false))

    override fun onBindViewHolder(h: VH, position: Int) {
        val e = items[position]
        h.title.text = e.title
        h.time.text = "🕒  ${e.time}"
        h.location.text = "📍  ${e.location}"
        h.description.text = e.description
    }

    override fun getItemCount() = items.size

    fun submit(newItems: List<Event>) {
        items = newItems
        notifyDataSetChanged()
    }
}

// ---------- Screen ----------
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventsScreen(
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onHomeClick: () -> Unit,
    onArticlesClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.events, null)

            // ---- Bottom nav ----
            view.findViewById<ImageButton>(R.id.chatButton).setOnClickListener { onChatClick() }
            view.findViewById<ImageButton>(R.id.forumButton).setOnClickListener { onForumClick() }
            view.findViewById<ImageButton>(R.id.homeButton).setOnClickListener { onHomeClick() }
            view.findViewById<ImageButton>(R.id.articlesButton).setOnClickListener { onArticlesClick() }
            view.findViewById<ImageButton>(R.id.profileButton).setOnClickListener { onProfileClick() }

            // ---- Event data (replace with your real data / ViewModel) ----
            val today = LocalDate.now()
            val allEvents = listOf(
                Event(
                    title = "Seeing the Invisible - Webinar",
                    date = LocalDate.parse("2026-10-20"),
                    time = "6:00 PM – 9:00 PM",
                    location = "Online",
                    description = "Presented by Dr Alecia Macrow, the webinar addresses the clinical approaches that are used to manage endometriosis symptoms.\n" +
                            "A core focus of this session is empathetic communication and delivering healthcare that feels individualised and supportive. Building on this, we'll examine how to identify early signs of complex pelvic pain, before turning to the diagnostic and treatment challenges that often arise, and finally, how to guide patients through their continued care.\n" +
                            "For more information see the link below!\n" +
                            "https://sapmea.asn.au/education/events/endometriosis-seeing-the-invisible-webinar-151026"
                ),
                Event(
                    title = "AEF Book Club",
                    date = LocalDate.parse("2026-10-20"),
                    time = "6:00 PM – 9:00 PM",
                    location = "Sky Bar at Shell House37 Margaret Street Sydney",
                    description = "Hosted by volunteers Gemma and Sam representing the Australian Endometriosis Foundation, this book club provides an opportunity for young women to connect with other young people who also experience endometriosis.\n" +
                            "Over a drink covered by the Foundation, you are able to meet new people and build new relationships over the book, life and so much more!\n" +
                            "Sign up to be an AEF member through the first link, then reserve your ticket with the second link!\n" +
                            "https://www.ausendofoundation.org.au/become-a-member\n" +
                            "https://www.ausendofoundation.org.au/events/off-the-page-weyward-oct26"
                ),
                Event(
                    title = "Exercise and Endo Webinar",
                    date = LocalDate.parse("2026-10-21"),
                    time = "6:00 PM – 7:00 PM",
                    location = "Online",
                    description = "Again organised by the Australian Endometriosis Foundation, Dr Tegan\n" +
                            "Hartmann and Dr Danielle Girard, shares their evidence-based insights on the relationship between exercise and endometriosis.\n" +
                            "Join us as we delve into their latest research exploring the relationship between movement, exercise, fatigue and pain.\n" +
                            "Make sure to sign up to explore more on endometriosis, exercise and management!\n" +
                            "https://www.ausendofoundation.org.au/events/exercise-and-endometriosis-october-2026"
                ),
                Event(
                    title = "Qendomeet - Online Peer Support",
                    date = LocalDate.parse("2026-11-04"),
                    time = "7:00 PM – 8:00 PM",
                    location = "Online",
                    description = "This zoom support group is a safe haven for those experiencing a variety of women's health conditions including endometriosis, PCOS, infertility and adenomyosis.\n" +
                            "You are welcome to share your stories with individuals who are able to connect and understand your struggles. Additionally, you will be able to hear other pain management methods and receive a welcoming support system.\n" +
                            "https://www.qendo.org.au/events/psgnov"
                ),
                Event(
                    title = "AEF X Fluid-Form Bondi",
                    date = LocalDate.parse("2026-11-08"),
                    time = "9:00 AM – 12:00 PM",
                    location = "FluidForm Pilates 136-138 Curlewis St, Bondi Beach NSW 2026",
                    description = "This is Australian Endometriosis Foundation's first event for November and it comes as an exciting one! They have been able to partner with Fluid Form Pilates studio in Bondi to curate a wellness event, promoting community, mindfulness and movement for all individuals.\n" +
                            "For everyone participating, they are able to walk away with a goodie bag featuring brands like Naked Sundays, Bouf Haircare and other lifestyle brands\n" +
                            "Enjoy a day of calmness knowing that all proceeds are going towards the foundation\n" +
                            "https://www.ausendofoundation.org.au/events/aef-wellness-collaboration-2026"
                ),
                Event(
                    title = "Coffee Catch Up Clovelly",
                    date = LocalDate.parse("2026-11-15"),
                    time = "10:00 AM – 11:30 AM",
                    location = "Angry Gnome - 355 Clovelly Road, Clovelly",
                    description = "The Australian Endometriosis Foundation continually encourages new members to take part in the foundation and this is the perfect event for it! Whether you are brand new to the AEF community or frequent their events, go ahead to make new friends over a cup of coffee. A complimentary coffee and new friends are guaranteed!\n" +
                            "Just a heads-up that it's designed for young adults, as our conversations may touch on sensitive topics like infertility. We want this to be a space where everyone feels comfortable being open, so we really appreciate your understanding.\n" +
                            "https://www.ausendofoundation.org.au/events/clovelly-coffee-catch-up-nov26"
                ),
                Event(
                    title = "Bunnings Sausage Sizzle",
                    date = LocalDate.parse("2026-11-21"),
                    time = "8:00 AM – 5:00 PM",
                    location = "Bunnings Eastgardens - 140-148 Denison Street, Hillsdale",
                    description = "Now who doesn't love a Bunnings snag?\n" +
                            "An all-day fundraiser is being held by the incredible Australian Endometriosis Foundation at Bunnings Eastgardens to raise profits for education, awareness, advocacy and community programs targeted towards young people managing endometriosis.\n" +
                            "Volunteers who can work minimum 2hr shifts are encouraged to join the team, registration is in the link below!\n" +
                            "https://www.ausendofoundation.org.au/events/bunnings-eastgardens-sausage-sizzle-2026"
                ),
                Event(
                    title = "Wreath Making Workshop",
                    date = LocalDate.parse("2026-11-27"),
                    time = "6:00 PM – 8:00 PM",
                    location = "Vaucluse Yacht Club - Robertson Pl & Marine Parade, Watsons Bay",
                    description = "Get ready for the festive season!\n" +
                            "The Australian Endometriosis Foundation has partnered with THE STYLE MAISON to provide an abundance of equipment and beautiful florals, encouraging those experiencing endometriosis to learn how to craft a wreath.\n" +
                            "Each ticket is \$50, where all proceeds go towards the organisation of future events for the foundation.\n" +
                            "https://www.ausendofoundation.org.au/events/bunnings-eastgardens-sausage-sizzle-2026"
                ),
            )
            val eventsByDate = allEvents.groupBy { it.date }
            val eventDates = eventsByDate.keys

            // ---- State ----
            var currentMonth = YearMonth.now()
            var selectedDate: LocalDate = today

            val monthTitle = view.findViewById<TextView>(R.id.monthTitle)
            val dateLabel = view.findViewById<TextView>(R.id.selectedDateLabel)
            val noEvents = view.findViewById<TextView>(R.id.noEventsText)

            val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
            val dayFormatter = DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.getDefault())

            // Monday-start grid, padded to 6 rows so the height never jumps
            fun buildDays(month: YearMonth): List<LocalDate?> {
                val offset = month.atDay(1).dayOfWeek.value - 1
                val list = MutableList<LocalDate?>(offset) { null }
                list += (1..month.lengthOfMonth()).map { month.atDay(it) }
                while (list.size < 42) list.add(null)
                return list
            }

            lateinit var calendarAdapter: CalendarAdapter
            val eventAdapter = EventAdapter(emptyList())

            fun refresh() {
                monthTitle.text = currentMonth.format(monthFormatter)
                calendarAdapter.update(buildDays(currentMonth), selectedDate)

                dateLabel.text = selectedDate.format(dayFormatter)
                val todays = eventsByDate[selectedDate].orEmpty()
                eventAdapter.submit(todays)
                noEvents.visibility = if (todays.isEmpty()) View.VISIBLE else View.GONE
            }

            calendarAdapter = CalendarAdapter(buildDays(currentMonth), eventDates, selectedDate) { date ->
                selectedDate = date
                refresh()
            }

            view.findViewById<RecyclerView>(R.id.calendarRecyclerView).apply {
                layoutManager = GridLayoutManager(context, 7)
                adapter = calendarAdapter
            }
            view.findViewById<RecyclerView>(R.id.eventsRecyclerView).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = eventAdapter
            }

            // ---- Month arrows ----
            fun changeMonth(newMonth: YearMonth) {
                currentMonth = newMonth
                selectedDate = if (newMonth == YearMonth.from(today)) today else newMonth.atDay(1)
                refresh()
            }
            view.findViewById<View>(R.id.prevMonthButton).setOnClickListener { changeMonth(currentMonth.minusMonths(1)) }
            view.findViewById<View>(R.id.nextMonthButton).setOnClickListener { changeMonth(currentMonth.plusMonths(1)) }

            refresh()
            view
        }
    )
}