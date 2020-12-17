Feature: basic dev to functionalities
  Scenario: Select first podcast and play it
    Given DevTo main page is open
    When User click on podcasts
    And User select first podcast
    Then User can see its title
    And User can play it

   Scenario: Open first video page
     Given DevTo main page is open
     When User click on Videos
     And User select first video
     Then First video is played
## komentarz: dopisac sobie wlaczenie video bo na razie mam krok do wyswietlenia
  Scenario: Searching for right phrase
    Given DevTo main page is open
    When User search "testing"
    Then Top result should contain the word "testing"