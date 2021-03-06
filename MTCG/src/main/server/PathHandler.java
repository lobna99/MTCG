package server;

import Http.HTTPRequestImpl;
import Json.ParseJsonImpl;
import Json.ParseJson;
import battle.BattleRequest;
import cards.CardRequest;
import deck.DeckRequest;
import packages.PackageRequest;
import scoreboard.ScoreRequest;
import sessions.LoginRequest;
import stats.StatsRequest;
import trading.TradingRequest;
import transactions.PurchaseRequest;
import user.UserRequest;

import java.io.IOException;
import java.sql.SQLException;

public interface PathHandler {
    final CardRequest cardRequest = new CardRequest();
    final BattleRequest battleRequest = new BattleRequest();
    final DeckRequest deckRequest = new DeckRequest();
    final PackageRequest packageRequest = new PackageRequest();
    final ScoreRequest scoreRequest = new ScoreRequest();
    final StatsRequest statsRequest = new StatsRequest();
    final LoginRequest loginRequest = new LoginRequest();
    final PurchaseRequest purchaseRequest = new PurchaseRequest();
    final UserRequest userRequest = new UserRequest();
    final ParseJson Json = new ParseJsonImpl();
    final TradingRequest tradingRequest = new TradingRequest();

    public void handlePath(HTTPRequestImpl request) throws IOException, InterruptedException, SQLException;
}
