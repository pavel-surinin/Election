var App = React.createClass({
  getInitialState: function() {
    return {
      userLogged: 'svecias',
    };
  },
  componentWillMount: function() {
    var self = this;
    axios
    .get('users/logged')
    .then(function(response){
      self.setState({userLogged : response.data});
    })
    .catch(function(err){
      console.error('axios error at App', err);
    });
  },
  render: function() {
    return (
      <div style={{ paddingTop: '20px' }}>
        <nav className="navbar navbar-default">
          <div className="container-fluid">
              <ul className="nav navbar-nav">
                <li><a href="#/">Pradinis</a></li>
                <li><a href="#/login">Prisijungti</a></li>
                <li><a href="#/representative">Atstovas</a></li>

              </ul>
              <ul className="nav navbar-nav navbar-right">
                <li><a href="#/">{this.state.userLogged}</a></li>
              </ul>
          </div>
        </nav>
        {this.props.children}
      </div>
    );
  }
});

window.App = App;

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
    return <div>Route did not match</div>;
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

      <Route path="*" component={NoMatch}/>
    </Route>
  </Router>
), document.getElementById('root'));
