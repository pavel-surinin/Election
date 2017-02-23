var EmptyComponent = React.createClass({
  render: function() {
    return (
      <div />
    );
  }

});

window.EmptyComponent = EmptyComponent;

var NoMatch = React.createClass({
  render: function() {
    return <div>404 klaida, puslapis nerastas</div>;
  }
});

var Router = ReactRouter.Router;
var Route = ReactRouter.Route;
var IndexRoute = ReactRouter.IndexRoute;
var hashHistory = ReactRouter.hashHistory;

ReactDOM.render((
  <Router history={hashHistory}>
    <Route path="/representative" component={RepresentativeContainer}>
      <IndexRoute component={RepHomeContainer} />
      <Route path="/representative/single" component={SingleResultContainer} />
      <Route path="/representative/multi" component={MultiResultContainer} />
      <Route path="*" component={RepHomeContainer}/>
    </Route>
    <Route path="/admin" component={AdminComponent}>
      <IndexRoute component={EmptyComponent} />
      <Route path="/admin/district" component={DistrictListContainer} />
      <Route path="/admin/district/create" component={DistrictCreateContainer} />
      <Route path="/admin/district/edit/:id" component={DistrictEditContainer} />
      <Route path="/admin/candidate" component={CandidateContainer} />
      <Route path="/admin/candidate/edit/:id" component={CandidateEditContainer} />
      <Route path="/admin/representative" component={DistrictRepresentativeContainer} />
      <Route path="/admin/representative/create" component={DistrictRepresentativeCreateContainer} />
      <Route path="/admin/county" component={CountyContainer} />
      <Route path="/admin/county/create" component={CountyCreateContainer} />
      <Route path="/admin/county/edit/:id" component={CountyEditContainer} />
      <Route path="/admin/county/:id" component={CountyDetailViewContainer} />
      <Route path="/admin/party" component={PartyContainer} />
      <Route path="/admin/party/create" component={PartyCreateContainer} />
      <Route path="/admin/party/:id" component={PartyDetailViewContainer} />
      <Route path="/admin/party/edit/:id" component={PartyEditContainer} />
      <Route path="/admin/results" component={ResultsListContainer} />
      <Route path="*" component={NoMatch}/>
    </Route>

    <Route path="/" component={MenuComponent}>
      <IndexRoute component={HomeComponent} />
      <Route path="/login" component={LoginContainer} />
      <Route path="/parties" component={EmptyComponent} />
      <Route path="/counties" component={CountiesContainer}/>
      <Route path="/counties/:county" component={CountyResultsContainer}/>
      <Route path="/counties/:county/:id" component={DistrictResultsContainer}/>
      <Route path="/results" component={GeneralResultsContainer} />
      <Route path="/contacts" component={EmptyComponent} />
      <Route path="/candidates" component={CandidatesListContainer} />
      <Route path="*" component={NoMatch}/>
    </Route>
  </Router>
), document.getElementById('root'));
