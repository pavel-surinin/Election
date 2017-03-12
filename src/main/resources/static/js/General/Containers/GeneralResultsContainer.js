function getGeneralResults(self) {
  axios
  .get('results/general/')
  .then(function(response){
    self.setState({
      results :  response.data,
      isLoading : false,
    });
  })
  .catch(function(err){
    console.error('DistrictResultsContainer.componentWillMount.axios', err);
  });
}

var GeneralResultsContainer = React.createClass({
  getInitialState: function() {
    return {
      results:[],
      isLoading:true,
    };
  },
  componentDidMount: function() {
    $('#collapseOne').collapse('hide');
    getGeneralResults(this);
  },
  componentWillReceiveProps: function(nextProps) {
    getGeneralResults(this);
  },
  render: function() {
    document.title='Rezultatai - Rinkimai 2017';
    if (this.state.isLoading) {
      return <div style={{marginTop : '3px', color : 'white', textAlign : 'center'}}><i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i></div>;
    }
    return (
      <GeneralresultsComponent
        results={this.state.results}
      />
    );
  }
});

window.GeneralResultsContainer = GeneralResultsContainer;
