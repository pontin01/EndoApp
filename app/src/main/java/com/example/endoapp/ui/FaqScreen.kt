package com.example.endoapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.endoapp.R
import com.example.endoapp.database.Faq
import com.example.endoapp.database.User
import com.example.endoapp.database.functions.FaqService
import com.example.endoapp.state.UserDetails

class FaqAdapter(private var items: List<Faq>) : RecyclerView.Adapter<FaqAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val card: View = v.findViewById(R.id.faqCard)
        val question: TextView = v.findViewById(R.id.questionText)
        val likes: TextView = v.findViewById(R.id.likeCountText)
        val tag: TextView = v.findViewById(R.id.tagText)
        val answerBox: View = v.findViewById(R.id.answerContainer)
        val answer: TextView = v.findViewById(R.id.answerText)
        val arrow: TextView = v.findViewById(R.id.expandArrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_faq, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val faq = items[position]
        holder.question.text = faq.question
        holder.likes.text = "♥ ${faq.likes}"
        holder.tag.text = faq.tag
        holder.answer.text = faq.answer
        holder.answerBox.visibility = if (faq.expanded) View.VISIBLE else View.GONE
        holder.arrow.rotation = if (faq.expanded) 90f else 0f

        holder.card.setOnClickListener {
            faq.expanded = !faq.expanded
            notifyItemChanged(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount() = items.size

    fun submit(newItems: List<Faq>) {
        items = newItems
        notifyDataSetChanged()
    }
}

@Composable
fun FaqScreen(
    onChatClick: () -> Unit,
    onForumClick: () -> Unit,
    onHomeClick: () -> Unit,
    onArticlesClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    var faqs by remember { mutableStateOf<List<Faq>>(emptyList()) }

    LaunchedEffect(Unit) {
        faqs = FaqService().getFaqs()
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.faq, null)

            view.findViewById<ImageButton>(R.id.chatButton)
                .setOnClickListener {
                    onChatClick()
                }

            view.findViewById<ImageButton>(R.id.forumButton)
                .setOnClickListener {
                    onForumClick()
                }

            view.findViewById<ImageButton>(R.id.homeButton)
                .setOnClickListener {
                    onHomeClick()
                }

            view.findViewById<ImageButton>(R.id.articlesButton)
                .setOnClickListener {
                    onArticlesClick()
                }

            view.findViewById<ImageButton>(R.id.profileButton)
                .setOnClickListener {
                    onProfileClick()
                }

            val allFaqs = listOf(
                Faq(
                    "What is Endometriosis?",
                    "Endometriosis occurs when the endometrium-like tissue, tissue similar to the lining the uterus, is found outside of the uterus, causing inflammation and scar tissue formation, It is a chronic disease and commonly occurs in the pelvis (e.g. in the pelvic cavity or on the ovaries), but can also occur elsewhere in the body. https://www.who.int/news-room/fact-sheets/detail/endometriosis",
                    "Disease", 24
                ),
                Faq(
                    "What causes endometriosis?",
                    "Although endometriosis is quite common, affecting around 1 in 7 females at birth in Australia, there is actually quite little known about what causes it. We currently know that it is an inflammatory disorder where there are abnormal levels of inflammatory response. However, what causes to the endometrial tissue to grow outside the tissue and why the inflammatory response is abnormal is still under investigation.\n" +
                            "https://www.who.int/news-room/fact-sheets/detail/endometriosis",
                    "Disease", 18
                ),
                Faq(
                    "What are common symptoms of endometriosis?",
                    "Common symptoms include: 1. heavy, painful periods or irregular bleeding  2. abdominal or pelvic pain with your period, sex and when going using the toilet  3. feeling bloated  4. back pain during periods  5. tiredness and fatigue  6. infertility or difficulty becoming pregnant\n" +
                            "https://www.healthdirect.gov.au/endometriosis\n" +
                            "https://www.thewomens.org.au/health-information/periods/endometriosis/about-endometriosis",
                    "Symptoms", 9
                ),
                Faq(
                    "Is endometriosis preventable?",
                    "Unfortunately the disease isn't preventable as we currently do not know the exact cause. However, you can lower the risk of developing endometriosis or worsening the condition with medical management and anti-inflammatory habits and target exercise/physiotherapy. \n" +
                            "With further research and enhanced awareness, early diagnosis and better quality of care may slow or stop the progression of the disease and reduce long term symptoms. \n" +
                            "https://www.thewomens.org.au/health-information/periods/endometriosis/treating-endometriosis\n" +
                            "https://www.healthdirect.gov.au/endometriosis ",
                    "Prevention", 0
                ),
                Faq(
                    "Is endometriosis genetic?",
                    "There is a strong genetic link in endometriosis with around 50% of the development risk coming from genetics. The other 50% is attributed from environmental factors like hormones, inflammation, diet, etc. There is also an increased expression of inflammatory response genes (which encode for increased inflammatory response seen in the disease) https://pmc.ncbi.nlm.nih.gov/articles/PMC4346178/ ",
                    "Who is affected?", 0
                ),
                Faq(
                    "Is endometriosis hereditary?",
                    "Although we currently don’t know what exactly causes endometriosis, we do believe that both genetics and evironmental factors play into disease development. Around 50% of the risk of developing the condition is genetic, with a strong link between family history and increased likelihood of developing the disease. https://pmc.ncbi.nlm.nih.gov/articles/PMC4346178/ ",
                    "Who is affected?", 0
                ),
                Faq(
                    "Is endometriosis cancer?",
                    "No, endometriosis is not cancer. It is a non-cancerous (benign) condition where endometrial-like tissue grows outside the uterus. Although it is not cancer, there is evidence that it having endometriosis may increase the risk of epithelial ovarian-cancers (EOCs) and endometrial cancer. https://ocrahope.org/news/endometriosis-and-endometrial-cancer-differences/ https://pmc.ncbi.nlm.nih.gov/articles/PMC4346178/  ",
                    "Disease", 0
                ),
                Faq(
                    "Can you have endometriosis if you have no symptoms?",
                    "Yes you can still have endometriosis if you have no symptoms, as the severity of symptoms doesn't correlate with disease severity. Those with mild endometriosis might be unaware that they are living with it as they are asymptomatic and can go their entire lives without knowing. Despite this, endometriosis can still affect your body and fertility. Additionally, symptoms are likely to occur if the disease progresses towards later stages (3-4) https://www.who.int/news-room/fact-sheets/detail/endometriosis",
                    "Symptoms", 0
                ),
                Faq(
                    "Can endometriosis affect you at any age?",
                    "Yes, endometriosis can affect you across any age. However, diagnosis most commonly occurs during reproductive years as one of the main symptoms is heavy, painful periods and symptoms usually improve after menopause due to decreased eostrogen. https://internationalendo.com/endometriosis-age-affected/ https://www.who.int/news-room/fact-sheets/detail/endometriosis",
                    "Who is affected?", 0
                ),
                Faq(
                    "Can endometriosis be cured?",
                    "Unfortunately, there are currently no exisiting treatments that can cure the disease. However, many of the treatments today focus on symptoms, severity and the preferences of each individual. https://www.who.int/news-room/fact-sheets/detail/endometriosis ",
                    "Disease", 0
                ),
                Faq(
                    "Who is affected by endometriosis?",
                    "Anyone with a uterus and menstrual cycles. This includes individuals who are female at birth, transgender men, intersex individuals and non-binary people. https://www.who.int/news-room/fact-sheets/detail/endometriosis https://internationalendo.com/endometriosis-age-affected/ ",
                    "Who is affected?", 0
                ),
                Faq(
                    "How long does it take to get diagnosed?",
                    "Unfortunately getting diagnosed with endometriosis can be a complicated process as endometriosis symptoms are variable and non-specific to the disease. This means that diagnosis is often a process of trial and error. Currently, the average time for diagnosis, according the World Health Organisation is 4-12 years. https://www.who.int/news-room/fact-sheets/detail/endometriosis ",
                    "Disease", 0
                ),
            ).sortedByDescending { it.likes }

            val tags = listOf("All") + allFaqs.map { it.tag }.distinct()

            // ---- List ----
            val adapter = FaqAdapter(allFaqs)
            view.findViewById<RecyclerView>(R.id.faqRecyclerView).apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }

            // ---- Filter dropdown ----
            val spinner = view.findViewById<Spinner>(R.id.tagFilterSpinner)
            spinner.adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                tags
            )
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                    val selected = tags[position]
                    adapter.submit(
                        if (selected == "All") allFaqs else allFaqs.filter { it.tag == selected }
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            view
        }
    )
}