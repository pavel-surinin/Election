<<<<<<< HEAD
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
      console.log('this index:',this);
      return (
      <div style={{ paddingTop: '20px' }}>
        <nav className="navbar navbar-default">
          <div className="container-fluid">
              <ul className="nav navbar-nav">
                <li><a href="#/">Pradinis</a></li>
                <li><a href="#/login">Prisijungti</a></li>
                <li><a href="#/representative">Atstovas</a></li>
                <li><a href="#/candidate">Kandidatai</a></li>
                  <li className="dropdown">
                    <a className="dropdown-toggle" data-toggle="dropdown">Results district
                    <span className="caret"></span></a>
                    <ul className="dropdown-menu">
                      <li><a href="#/results/district/1">Page Results district 1</a></li>
                      <li><a href="#/results/district/2">Page Results district 2</a></li>
                      <li><a href="#/results/district/3">Page Results district 3</a></li>
                      <li><a href="#/results/district/4">Page Results district 4</a></li>
                      <li><a href="#/results/district/5">Page Results district 5</a></li>
                      <li><a href="#/party">Party</a></li>
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

=======
>>>>>>> 63f00e33c0f2aec8adced5224dacbbe7d9b85689
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
    document.title='Puslapis nerastas. 404 klaida';
    return(
      <div>
      <div style={{marginTop: '50px'}} className="jumbotron alert alert-danger erpage">
        <h1><i className="fa fa-meh-o" aria-hidden="true"></i> Puslapis nerastas</h1>
        <p>Įvestas puslapio adresas nerastas.</p>
      </div>
    </div>);
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
      <Route path="/admin/results" component={ResultsListContainer} />
      <Route path="*" component={NoMatch}/>
    </Route>

    <Route path="/" component={MenuComponent}>
      <IndexRoute component={HomeComponent} />
      <Route path="/login" component={LoginContainer} />
<<<<<<< HEAD
      <Route path="/candidates" component={CandidatesListContainer} />
      <Route path="/parties" component={PartyViewContainer} />
      <Route path="/parties/:id" component={PartyDetailContainer} />
=======
      <Route path="/parties" component={EmptyComponent} />
>>>>>>> 63f00e33c0f2aec8adced5224dacbbe7d9b85689
      <Route path="/counties" component={CountiesContainer}/>
      <Route path="/counties/:county" component={CountyResultsContainer}/>
      <Route path="/counties/:county/:id" component={DistrictResultsContainer}/>
      <Route path="/results" component={GeneralResultsContainer} />
      <Route path="/contacts" component={EmptyComponent} />
<<<<<<< HEAD
=======
      <Route path="/candidates" component={CandidatesListContainer} />
>>>>>>> 63f00e33c0f2aec8adced5224dacbbe7d9b85689
      <Route path="*" component={NoMatch}/>
    </Route>
  </Router>
), document.getElementById('root'));
