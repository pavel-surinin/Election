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
      return <div><img src='images/loading.gif'/></div>;
    }
    return (
      <GeneralresultsComponent
        results={this.state.results}
      />
    );
  }
});

window.GeneralResultsContainer = GeneralResultsContainer;
