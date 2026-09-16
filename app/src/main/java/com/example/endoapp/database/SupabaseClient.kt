package com.example.endoapp.database

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://fcsuvrtgvcqnlmpwbdlw.supabase.co",
        supabaseKey = "sb_publishable_izRTC2ZrHL09brqnqPmfTg_yY17NIiE"
    ) {
        install(Postgrest)
    }
}