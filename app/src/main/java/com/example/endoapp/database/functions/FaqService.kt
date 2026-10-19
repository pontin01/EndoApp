package com.example.endoapp.database.functions

import com.example.endoapp.database.Faq
import com.example.endoapp.database.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class FaqService {
    suspend fun getFaqs() : List<Faq> {
        val faqs = supabase.from("faq")
            .select(columns = Columns.list("question,answer,tag,likes")) {  }
            .decodeList<Faq>()

        return faqs
    }
}