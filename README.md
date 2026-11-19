# Steam Android Hub

Fanowski panel do uruchamiania oficjalnego Steam Link na Androidzie 13+.

> **Ważne:** ta aplikacja nie uruchamia binarnych gier Windows bezpośrednio na telefonie i nie zawiera własnego kodeka streamingu. Gra działa na komputerze z uruchomionym Steam, a obraz i sterowanie przesyła oficjalny klient Steam Link. Ten projekt jest niezależny i nie jest powiązany z Valve.

## Aktualny zakres — v0.1.0

- Natywna aplikacja Android (Java + Android SDK).
- Minimalna wersja Androida: 13 / API 33.
- Target SDK: 36.
- Sprawdzenie, czy Steam Link jest zainstalowany.
- Przycisk uruchomienia Steam Link; gdy aplikacji brakuje, przejście do jej strony w Google Play.
- Krótka instrukcja przygotowania komputera i połączenia telefonu.
- Brak logowania, telemetrii, reklam i zbierania danych.
- GitHub Actions buduje APK debug po pushu / ręcznym uruchomieniu workflow.

## Jak używać

1. Na komputerze zainstaluj i uruchom Steam oraz zaloguj się.
2. W Steam na komputerze włącz **Ustawienia → Remote Play → Włącz Remote Play**.
3. Telefon i komputer połącz z tą samą siecią Wi-Fi. Do pierwszej konfiguracji zalecana jest sieć 5 GHz; komputer najlepiej podłączyć Ethernetem.
4. Zainstaluj Steam Link z Google Play.
5. Uruchom **Steam Android Hub**, wybierz **Uruchom Steam Link**, a następnie sparuj telefon z komputerem zgodnie z instrukcjami Steam Link.
6. Po sparowaniu wybierz bibliotekę i grę w Steam Link. Komputer musi pozostać włączony.

## Budowanie APK

Wymagania:
- JDK 17
- Android SDK z platformą Android 36 i Build Tools 35.0.0
- Gradle 8.11.1 (albo wrapper z repozytorium)

W Android Studio otwórz katalog repozytorium i uruchom **Build → Build Bundle(s) / APK(s) → Build APK(s)**.

Z terminala:

```bash
./gradlew assembleDebug
```

Wynik:
`app/build/outputs/apk/debug/app-debug.apk`

## Automatyczny build

GitHub Actions: zakładka **Actions → Android debug APK**. Workflow można uruchomić ręcznie przez **Run workflow**. Gotowy plik APK będzie dostępny jako artifact `steam-android-hub-debug`.

## Architektura

```
Steam Android Hub (Android 13+)
  ├─ sprawdzenie instalacji Steam Link
  ├─ instrukcja parowania i konfiguracji PC
  └─ Android Intent → oficjalna aplikacja Steam Link
                              ├─ połączenie z komputerem
                              ├─ streaming obrazu i dźwięku
                              └─ przesyłanie sterowania
                                      ↓
                                Steam na PC → gra
```

Aplikacja otwiera Steam Link, ale nie próbuje sterować jego prywatnymi ekranami ani nie korzysta z nieudokumentowanych API Valve.

## Prywatność i bezpieczeństwo

- Brak uprawnień do mikrofonu, kamery, lokalizacji i plików.
- Brak własnego serwera i brak wysyłania danych do backendu.
- Aplikacja sprawdza wyłącznie, czy pakiet Steam Link jest dostępny na urządzeniu.
- Połączenie ze streamingiem i parowanie są obsługiwane przez Steam Link.

## Licencje i znaki towarowe

Kod tego repozytorium jest udostępniony na licencji MIT (plik `LICENSE`). Steam i Steam Link są znakami towarowymi Valve Corporation. Nazwy użyto wyłącznie w celu opisania kompatybilności. Nie używamy oficjalnego logo ani nie sugerujemy, że aplikacja jest produktem Valve.

## Plan rozwoju

- **v0.1.0:** panel Android, wykrywanie Steam Link, uruchamianie klienta i instrukcja.
- **v0.2.0:** lista zapisanych komputerów, test dostępności hosta w sieci lokalnej i konfiguracja wygodnych skrótów.
- **v0.3.0:** analiza możliwości integracji niezależnego klienta streamingowego (np. Sunshine/Moonlight) z poszanowaniem jego licencji i autorstwa.

