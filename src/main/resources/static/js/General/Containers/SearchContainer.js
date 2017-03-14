function searchFor(aaa,self) {
  var data = new FormData();
  data.append('searchFor' , aaa);
  var header = { headers: {
    'Content-Type': 'multipart/form-data'}};
  axios
    .post('/search', data, header)
    .then(function(response){
      self.setState({
        results :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('CandidatesListContainer.componentWillMount.axios.get.candidate', err);
    });
}
var SearchContainer = React.createClass({
  getInitialState: function() {
    return {
      isLoading : true,
      results : [],
    };
  },
  componentDidMount: function() {
    searchFor(this.props.params.string,this);

  },
  componentWillReceiveProps: function(nextProps) {
    searchFor(nextProps.params.string,this);
  },
  render: function() {
    document.title = 'Ieškoti: ' + this.props.params.string + ' - Rinkimai 2017';
    if (this.state.isLoading) {
      return <div style={{marginTop : '3px', color : 'white', textAlign : 'center'}}><i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i></div>;
    }
    return (
      <SearchComponent searchFor={this.props.params.string} results={this.state.results}/>
    );
  }

});

window.SearchContainer = SearchContainer;
