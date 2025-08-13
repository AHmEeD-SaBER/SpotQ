import java.util.Locale

fun Double.toOneDecimalString(locale: Locale): String {
    return String.format(locale, "%.1f", this)
}
