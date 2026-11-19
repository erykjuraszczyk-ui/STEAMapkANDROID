package com.erykjuraszczyk.steamandroidhub;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fan-made onboarding and launcher companion for the official Steam Link app.
 *
 * This app does not install or execute Windows games locally. Steam runs the
 * game on the PC; Steam Link handles pairing, streaming, audio and input.
 */
public final class MainActivity extends Activity {
    private static final String STEAM_LINK_PACKAGE = "com.valvesoftware.steamlink";
    private static final String STEAM_LINK_PLAY_URL =
            "https://play.google.com/store/apps/details?id=com.valvesoftware.steamlink";
    private static final String REMOTE_PLAY_URL = "https://store.steampowered.com/remoteplay";

    private static final int BACKGROUND = Color.rgb(8, 17, 29);
    private static final int CARD = Color.rgb(17, 31, 48);
    private static final int TEXT_PRIMARY = Color.rgb(238, 245, 250);
    private static final int TEXT_SECONDARY = Color.rgb(163, 181, 197);
    private static final int ACCENT = Color.rgb(101, 223, 164);

    private TextView steamLinkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BACKGROUND);
        getWindow().setNavigationBarColor(BACKGROUND);
        setContentView(createScreen());
        refreshSteamLinkStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSteamLinkStatus();
    }

    private View createScreen() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(BACKGROUND);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20), dp(24), dp(20), dp(32));
        scrollView.addView(content, new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT));

        TextView eyebrow = makeText("FAN PROJECT  •  ANDROID 13+", 12, ACCENT, true);
        content.addView(eyebrow);

        addSpace(content, 10);

        TextView title = makeText("STEAM ANDROID HUB", 27, TEXT_PRIMARY, true);
        content.addView(title);

        addSpace(content, 8);

        TextView subtitle = makeText(
                "Twoja biblioteka gier PC na ekranie telefonu. Połącz się z komputerem i graj zdalnie.",
                16, TEXT_SECONDARY, false);
        content.addView(subtitle);

        addSpace(content, 22);

        LinearLayout statusCard = makeCard();
        statusCard.addView(makeText("KLIENT STEAM LINK", 12, TEXT_SECONDARY, true));
        addSpace(statusCard, 8);
        steamLinkStatus = makeText("Sprawdzanie instalacji…", 15, TEXT_PRIMARY, true);
        statusCard.addView(steamLinkStatus);
        content.addView(statusCard);

        addSpace(content, 12);

        Button launchButton = makeButton("URUCHOM / ZAINSTALUJ STEAM LINK", ACCENT, BACKGROUND);
        launchButton.setOnClickListener(view -> launchOrInstallSteamLink());
        content.addView(launchButton, fullWidthParams());

        addSpace(content, 10);

        Button guideButton = makeButton("Instrukcja Steam Remote Play", CARD, TEXT_PRIMARY);
        guideButton.setOnClickListener(view -> openUrl(REMOTE_PLAY_URL));
        content.addView(guideButton, fullWidthParams());

        addSpace(content, 26);
        content.addView(makeText("PIERWSZE URUCHOMIENIE", 13, ACCENT, true));
        addSpace(content, 12);

        addStep(content, "01", "Przygotuj komputer",
                "Włącz komputer, uruchom Steam i zaloguj się w oficjalnym kliencie.");
        addStep(content, "02", "Włącz Remote Play",
                "W Steam na PC otwórz Ustawienia → Remote Play i włącz tę funkcję.");
        addStep(content, "03", "Połącz urządzenia",
                "Na pierwsze parowanie połącz telefon i komputer z tą samą siecią. Zalecane jest Wi-Fi 5 GHz, a komputer najlepiej podłączyć przewodowo.");
        addStep(content, "04", "Sparuj i wybierz grę",
                "Otwórz Steam Link, wybierz komputer i potwierdź kod parowania. Następnie wybierz grę z biblioteki Steam.");

        addSpace(content, 14);

        LinearLayout noteCard = makeCard();
        noteCard.addView(makeText("WAŻNE: GDZIE DZIAŁA GRA?", 13, ACCENT, true));
        addSpace(noteCard, 7);
        noteCard.addView(makeText(
                "Gra Steam PC jest instalowana i uruchamiana na komputerze. Telefon odbiera obraz i dźwięk oraz wysyła sterowanie. Ten projekt nie instaluje plików EXE ani gier Windows bezpośrednio na Androidzie.",
                14, TEXT_PRIMARY, false));
        content.addView(noteCard);

        addSpace(content, 16);

        TextView privacy = makeText(
                "PRYWATNOŚĆ  •  Aplikacja nie prosi o hasło Steam, nie zbiera danych, nie używa reklam ani własnego serwera. Parowanie i streaming obsługuje oficjalna aplikacja Steam Link.",
                12, TEXT_SECONDARY, false);
        content.addView(privacy);

        addSpace(content, 14);

        TextView disclaimer = makeText(
                "Nieoficjalny projekt fanowski. Nie jest powiązany z Valve Corporation. Steam i Steam Link są znakami towarowymi ich właściciela.",
                11, TEXT_SECONDARY, false);
        content.addView(disclaimer);

        return scrollView;
    }

    private void addStep(LinearLayout parent, String number, String heading, String description) {
        LinearLayout step = makeCard();
        step.setOrientation(LinearLayout.HORIZONTAL);

        TextView badge = makeText(number, 14, ACCENT, true);
        badge.setGravity(Gravity.CENTER);
        badge.setBackground(roundRect(Color.rgb(29, 63, 62), 14));
        LinearLayout.LayoutParams badgeParams = new LinearLayout.LayoutParams(dp(44), dp(44));
        badgeParams.rightMargin = dp(13);
        step.addView(badge, badgeParams);

        LinearLayout textColumn = new LinearLayout(this);
        textColumn.setOrientation(LinearLayout.VERTICAL);
        textColumn.addView(makeText(heading, 15, TEXT_PRIMARY, true));
        addSpace(textColumn, 5);
        textColumn.addView(makeText(description, 13, TEXT_SECONDARY, false));
        step.addView(textColumn, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        LinearLayout.LayoutParams stepParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        stepParams.bottomMargin = dp(9);
        parent.addView(step, stepParams);
    }

    private LinearLayout makeCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        card.setBackground(roundRect(CARD, 18));
        return card;
    }

    private TextView makeText(String value, int sizeSp, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(sizeSp);
        view.setTextColor(color);
        view.setGravity(Gravity.CENTER_VERTICAL);
        view.setLineSpacing(dp(2), 1.0f);
        if (bold) {
            view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        }
        return view;
    }

    private Button makeButton(String label, int fill, int textColor) {
        Button button = new Button(this);
        button.setText(label);
        button.setTextSize(14);
        button.setTextColor(textColor);
        button.setAllCaps(false);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setMinHeight(dp(54));
        button.setPadding(dp(14), dp(10), dp(14), dp(10));
        button.setBackground(roundRect(fill, 16));
        button.setElevation(dp(1));
        return button;
    }

    private GradientDrawable roundRect(int color, int radiusDp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radiusDp));
        return drawable;
    }

    private LinearLayout.LayoutParams fullWidthParams() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void addSpace(LinearLayout parent, int heightDp) {
        View space = new View(this);
        parent.addView(space, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(heightDp)));
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    private void refreshSteamLinkStatus() {
        if (steamLinkStatus == null) {
            return;
        }
        boolean installed = getPackageManager().getLaunchIntentForPackage(STEAM_LINK_PACKAGE) != null;
        if (installed) {
            steamLinkStatus.setText("Steam Link jest zainstalowany. Możesz otworzyć klienta.");
            steamLinkStatus.setTextColor(ACCENT);
        } else {
            steamLinkStatus.setText("Nie wykryto Steam Link. Zainstaluj oficjalną aplikację.");
            steamLinkStatus.setTextColor(TEXT_PRIMARY);
        }
    }

    private void launchOrInstallSteamLink() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(STEAM_LINK_PACKAGE);
        if (launchIntent != null) {
            try {
                startActivity(launchIntent);
            } catch (ActivityNotFoundException exception) {
                Toast.makeText(this, "Nie udało się uruchomić Steam Link.", Toast.LENGTH_LONG).show();
            }
            return;
        }
        openUrl(STEAM_LINK_PLAY_URL);
    }

    private void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(this, "Brak aplikacji do otwarcia tego adresu.", Toast.LENGTH_LONG).show();
        }
    }
}
