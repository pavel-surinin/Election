
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
      console.log(response.data);
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
                <li className="dropdown">
                  <a href="#" className="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Administratoriaus Menu <span className="caret"></span></a>
                  <ul className="dropdown-menu">
                    <li><a href="#/county">Apygardos</a></li>
                    <li><a href="#/district">Apylinkės</a></li>
                    <li><a href="#/rep">Apylinkių atstovai</a></li>
                    <li><a href="#/candidates">Kandidatai</a></li>
                    <li><a href="#/party">Partijos</a></li>
                  </ul>
                </li>
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
    <Route path="/" component={App}>
      <IndexRoute component={HomeComponent} />
      <Route path="/create" component={CreateBookContainer} />
      <Route path="/login" component={LoginContainer} />
      <Route path="/district" component={DistrictListContainer} />
      <Route path="/district/create" component={DistrictCreateContainer} />
      <Route path="/candidates" component={CandidateContainer} />
      <Route path="/candidates/createCandidate" component={CandidateEditContainer} />
      <Route path="/rep" component={DistrictRepresentativeContainer} />
      <Route path="/rep/createRep" component={DistrictRepresentativeCreateFormComponent} />
      <Route path="/county" component={CountyContainer} />
      <Route path="/county/create" component={CountyCreateContainer} />
      <Route path="/party" component={PartyContainer} />
      <Route path="/party/createParty" component={PartyCreateEditForm} />
      <Route path="*" component={NoMatch}/>
    </Route>
  </Router>
), document.getElementById('root'));
