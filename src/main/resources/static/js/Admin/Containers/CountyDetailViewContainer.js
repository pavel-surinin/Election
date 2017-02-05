var CountyDetailViewContainer = React.createClass({
  getInitialState: function() {
    return {
      countyDetails : null,
      isLoading : true,
    };
  },
  componentWillMount: function() {
    var self = this;
    axios
      .get('/county/' + this.props.params.id)
      .then(function(response){
        console.log(response.data);
        self.setState({
          countyDetails :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('CountyDetailViewContainer.componentWillMount.axios.get.county/:id', err);
      });
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      console.log(this.state.countyDetails);
      return (
        <CountyDetailViewComponent countyDetails={this.state.countyDetails}/>
      );
    }
  }
  });

window.CountyDetailViewContainer = CountyDetailViewContainer;
