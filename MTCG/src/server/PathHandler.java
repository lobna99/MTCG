package server;

import Http.HTTPRequest;
import Json.ParseJson;
import Json.ParseJsonInterface;
import battle.BattleRequest;
import cards.CardRequest;
import deck.DeckRequest;
import packages.PackageRequest;
import scoreboard.ScoreRequest;
import sessions.LoginRequest;
import stats.StatsRequest;
import transactions.PurchaseRequest;
import user.UserRequest;

import java.io.IOException;

public interface PathHandler {
    final CardRequest cardRequest=new CardRequest();
    final BattleRequest battleRequest=new BattleRequest();
    final DeckRequest deckRequest=new DeckRequest();
    final PackageRequest packageRequest=new PackageRequest();
    final ScoreRequest scoreRequest=new ScoreRequest();
    final StatsRequest statsRequest=new StatsRequest();
    final LoginRequest loginRequest=new LoginRequest();
    final PurchaseRequest purchaseRequest=new PurchaseRequest();
    final UserRequest userRequest=new UserRequest();
    final ParseJsonInterface Json=new ParseJson();
    public void handlePath(HTTPRequest request) throws IOException;
}
